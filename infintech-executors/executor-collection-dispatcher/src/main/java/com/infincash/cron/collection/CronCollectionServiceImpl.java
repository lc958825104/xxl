package com.infincash.cron.collection;

import static com.infincash.util.Jdk8DateUtils.dateSubstract;
import static com.infincash.util.Jdk8DateUtils.getDateAfter;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.infincash.cron.collection.mapper.TBizCollectionMapper;
import com.infincash.cron.collection.mapper.TBizCollectionOverdueBucketMapper;
import com.infincash.cron.collection.table.TBizCollection;
import com.infincash.cron.collection.table.TBizCollectionOverdueBucket;
import com.xxl.job.core.log.XxlJobLogger;

@Service
public class CronCollectionServiceImpl implements CronCollectionService
{
	@Autowired
	TBizCollectionMapper collectionMapper;

	@Autowired
	TBizCollectionOverdueBucketMapper bucketMapper;

	@Override
	public void assignCollection() throws InfintechException
	{
		//90天以上的是坏账
		String badDebtDay = getDateAfter(-90);
		List<TBizCollection> rList = collectionMapper.queryAll(badDebtDay);
		if (rList == null || rList.size() == 0)
		{
			throw new InfintechException("recordMapper.queryAll(badDebtDay) empty! badDebtDay: " + badDebtDay);
		}
		List<TBizCollectionOverdueBucket> bList = bucketMapper.queryAll();
		if (bList == null || bList.size() == 0)
		{
			throw new InfintechException("bucketMapper.queryAll() empty!");
		}
		Map<String, List<TBizCollection>> map = getWhichBucket(bList, rList);
		List<TBizCollection> resultList = Lists.newLinkedList();
		for (Entry<String, List<TBizCollection>> entry : map.entrySet())
		{
			String key = entry.getKey();
			List<Map<String, Object>> userList = collectionMapper.queryUserByRoleId(key);
			if (userList == null || userList.size()==0 ) {
				throw new InfintechException("collectionMapper.queryUserByRoleId(key) empty! key:" + key);
			}
			int cycle = userList.size();
			//FIXME 存量怎么办
			List<TBizCollection> list = entry.getValue();
			int a = 0;
			for (TBizCollection s : list) {
				XxlJobLogger.log("a: "+a+", cycle: "+cycle+", a%cycle:" + a%cycle);
				Map<String, Object> tmpMap = userList.get(a%cycle);
				String userId = (String) tmpMap.get("user_id");
				String userRealName = (String) tmpMap.get("real_name");
				s.setState(1);
				s.setFkSystemUser(userId);
				s.setCollectorLoginName(userRealName);
				long ll = dateSubstract(new Date(), s.getRepaymentDate());
				s.setOverdueDayCount(Short.valueOf("" + ll));
				String tmp = "";
				tmp += s.getDeadline();
				if ("0".equals(s.getUnit().trim()))
				{
					// 天
					tmp += " Day";
				} else if ("1".equals(s.getUnit().trim()))
				{
					// 月
					tmp += " Month";
				} else
				{
					throw new InfintechException("getUnit error! unit:" + s.getUnit());
				}
				s.setProjectPeriod(tmp);
				a++;
			}
			//拼接List
			resultList.addAll(list);
		}
		if (resultList.size()==0) {
			throw new InfintechException("resultList empty!");
		}
		XxlJobLogger.log("new");
		XxlJobLogger.log(resultList.get(0).toString());
		collectionMapper.insertBatch(resultList);
		
	}

	private Map<String, List<TBizCollection>> getWhichBucket(List<TBizCollectionOverdueBucket> bList, List<TBizCollection> rList)
	{
		// <k-v>:= <system_role_id - List<record>>
		Map<String, List<TBizCollection>> listMap = Maps.newHashMap();
		for (TBizCollection r : rList)
		{
			Date d        = r.getRepaymentDate();
			Date now      = new Date();
			long diffDate = dateSubstract(now, d);
			//FIXME 在新加坡服务器上跑无时差, 因为新加坡也是UTC+8 <==> Etc/UTC-8!
//			XxlJobLogger.log("d:" + d.toString() + ", now:" + now.toString() + ", diffDate:" + diffDate);
			for (int i = 0; i < bList.size() - 1; i++)
			{
				long lbegin = bList.get(i).getLeftClosedInterval().longValue();
				long lend = bList.get(i + 1).getLeftClosedInterval().longValue();
				if (diffDate >= lbegin && diffDate < lend)
				{
					//取system_role
					String systemRoleId = bList.get(i).gettSystemRoleId();
					List<TBizCollection> tmpList = listMap.get(systemRoleId);
					if (tmpList == null) {
						tmpList = Lists.newLinkedList();
						tmpList.add(r);
						listMap.put(systemRoleId, tmpList);
					} else {
						tmpList.add(r);
					}
				}
			}
		}
		return listMap;
	}

	@Override
	public int assignExemployeeCollection()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
