# -*- coding: utf-8 -*-
"""
This module provide kafka partition group consumer demo example.

Authors: scoot
Date:    2015/08/03 09:21:06
"""

import threading
from kafka.client import KafkaClient
from kafka.consumer import SimpleConsumer

class Consumer(threading.Thread):
    daemon = True

    def run(self):
        client = KafkaClient("10.206.216.13:19092,10.206.212.14:19092,10.206.209.25:19092")
        consumer = SimpleConsumer(client, "test-group", "jiketest")

        for message in consumer:
            print(message.message.value)

