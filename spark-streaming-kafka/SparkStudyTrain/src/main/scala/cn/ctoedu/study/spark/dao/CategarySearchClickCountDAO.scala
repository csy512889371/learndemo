package cn.ctoedu.spark.dao

import cn.ctoedu.spark.domain.{CategaryClickCount, CategarySearchClickCount}
import cn.ctoedu.spark.utils.HBaseUtils
import org.apache.hadoop.hbase.client.Get
import org.apache.hadoop.hbase.util.Bytes

import scala.collection.mutable.ListBuffer

/**
  *
  */
object CategarySearchClickCountDAO {

     val tableName = "categary_search_cout"
     val cf = "info"
     val qualifer = "click_count"

    /**
      * 保存数据
      * @param list
      */
    def save(list:ListBuffer[CategarySearchClickCount]): Unit ={
      val table =  HBaseUtils.getInstance().getHtable(tableName)
        for(els <- list){
            table.incrementColumnValue(Bytes.toBytes(els.day_search_categary),Bytes.toBytes(cf),Bytes.toBytes(qualifer),els.clickCout);
        }

    }

    def count(day_categary:String) : Long={
        val table  =HBaseUtils.getInstance().getHtable(tableName)
        val get = new Get(Bytes.toBytes(day_categary))
        val  value =  table.get(get).getValue(Bytes.toBytes(cf), Bytes.toBytes(qualifer))
         if(value == null){
           0L
         }else{
             Bytes.toLong(value)
         }
    }

    def main(args: Array[String]): Unit = {
       val list = new ListBuffer[CategarySearchClickCount]
        //list.append(CategarySearchClickCount("20171122_1_1",300))
        list.append(CategarySearchClickCount("20171122_2_1", 300))
        list.append(CategarySearchClickCount("20171122_1_2", 1600))
      //  save(list)

        print(count("20171122_www.sogou.com_2")+"---" )
    }

}
