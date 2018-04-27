# -*- coding: utf-8 -*-
"""
This module provide kafka partition and group consumer demo example.

Authors: scoot
Date:    2015/08/05 20:36:06
"""

import threading, time

from kafka.client import KafkaClient
from kafka.producer import SimpleProducer
from kafka.partitioner import HashedPartitioner

class SyncProducer(threading.Thread):
    daemon = True

    def run(self):
        client = KafkaClient("10.206.216.13:19092,10.206.212.14:19092,10.206.209.25:1909")
        producer = SimpleProducer(client)
        #producer = KeyedProducer(client,partitioner=HashedPartitioner)

        while True:
            producer.send_messages('jiketest', "test")
            producer.send_messages('jiketest', "test")

            time.sleep(1)
