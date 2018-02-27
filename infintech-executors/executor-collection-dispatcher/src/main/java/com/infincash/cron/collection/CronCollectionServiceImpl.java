package com.infincash.cron.collection;

import static com.infincash.util.Jdk8DateUtils.dateSubstract;
import static com.infincash.util.Jdk8DateUtils.getDateAfter;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
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
		List<TBizCollectionOverdueBucket> bList = bucketMapper.queryAll();
		if (bList == null || bList.size() == 0)
		{
			throw new InfintechException("bucketMapper.queryAll() empty!");
		}
		Short sBadDebtLimit = bList.get(bList.size()-1).getLeftClosedInterval();
		// 坏账的逾期天数
		long badDebtLimit = Long.valueOf(sBadDebtLimit.toString());
		badDebtLimit = -1 * (badDebtLimit - 1L);
		XxlJobLogger.log("\n\n>> badDebtLimit: "+badDebtLimit);
		String badDebtDay = getDateAfter(badDebtLimit);
		List<TBizCollection> rList = collectionMapper.queryAll(badDebtDay);
		if (rList == null || rList.size() == 0)
		{
			throw new InfintechException("recordMapper.queryAll(badDebtDay) empty! badDebtDay: " + badDebtDay);
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
			XxlJobLogger.log("\n\n``````````list size\n\n"+list.size());
			for (int x=0; x<5; x++) {
				XxlJobLogger.log("\n"+ x +"\n"+list.get(x).getProjectNumber());
			}
			for (int a=0; a<list.size(); a++) {
				//序列化深拷贝
				TBizCollection s = JSON.parseObject(JSON.toJSONString(list.get(a)), TBizCollection.class); 
				Map<String, Object> tmpMap = userList.get(a%cycle);
				String userId = new String((String) tmpMap.get("user_id"));
				String userRealName = new String((String) tmpMap.get("real_name"));
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
				resultList.add(s);
			}
		}
		if (resultList.size()==0) {
			throw new InfintechException("resultList empty!");
		}
		collectionMapper.insertBatch(resultList);
		
	}

	private Map<String, List<TBizCollection>> getWhichBucket(List<TBizCollectionOverdueBucket> bList, List<TBizCollection> rList)
	{
		// <k-v>:= <system_role_id - List<record>>
//		XxlJobLogger.log("rList size:" + rList.size());
		Map<String, List<TBizCollection>> listMap = Maps.newHashMap();
		for (TBizCollection r : rList)
		{
//			TBizCollection r = JSON.parseObject(JSON.toJSONString(item), TBizCollection.class); 
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
					// 1对1关系, 找到以后就跳出
					break;
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
