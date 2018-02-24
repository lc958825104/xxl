package com.infincash.cron.collection;

import static com.infincash.util.Jdk8DateUtils.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.infincash.cron.collection.mapper.TBizCollectionOverdueBucketMapper;
import com.infincash.cron.collection.mapper.TBizCollectionRecordMapper;
import com.infincash.cron.collection.table.TBizCollectionOverdueBucket;
import com.infincash.cron.collection.table.TBizCollectionRecord;
import com.xxl.job.core.log.XxlJobLogger;

public class CronCollectionServiceImpl implements CronCollectionService
{
	@Autowired
	TBizCollectionRecordMapper recordMapper;

	@Autowired
	TBizCollectionOverdueBucketMapper bucketMapper;

	@Override
	public void assignCollection() throws InfintechException
	{
		//90天以上的是坏账
		String badDebtDay = getDateAfter(-90);
		List<TBizCollectionRecord> rList = recordMapper.queryAll(badDebtDay);
		if (rList == null || rList.size() == 0)
		{
			throw new InfintechException("recordMapper.queryAll(badDebtDay) empty! badDebtDay: " + badDebtDay);
		}
		List<TBizCollectionOverdueBucket> bList = bucketMapper.queryAll();
		if (bList == null || bList.size() == 0)
		{
			throw new InfintechException("bucketMapper.queryAll() empty!");
		}
		getWhichBucket(bList, rList);
	}

	private Map<String, List<TBizCollectionRecord>> getWhichBucket(List<TBizCollectionOverdueBucket> bList, List<TBizCollectionRecord> rList)
	{
		// <k-v>:= <system_role_id - List<record>>
		Map<String, List<TBizCollectionRecord>> listMap = Maps.newHashMap();
		for (TBizCollectionRecord r : rList)
		{
			Date d        = r.getRepaymentDate();
			Date now      = new Date();
			long diffDate = dateSubstract(now, d);
			XxlJobLogger.log("d:" + d.toString() + ", now:" + now.toString() + ", diffDate:" + diffDate);
			for (int i = 0; i < bList.size() - 1; i++)
			{
				long lbegin = bList.get(i).getLeftClosedInterval().longValue();
				long lend = bList.get(i + 1).getLeftClosedInterval().longValue();
				if (diffDate >= lbegin && diffDate < lend)
				{
					//取system_role
					String systemRoleId = bList.get(i).gettSystemRoleId();
					List<TBizCollectionRecord> tmpList = listMap.get(systemRoleId);
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
