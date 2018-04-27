# -*- coding: utf-8 -*-
"""
This module provide kafka partition and group consumer demo example.

Authors: scoot
Date:    2015/08/02 15:30:06
"""


import logging, time
import partition_consumer


def main():

    threads = []
    partition = 3
    for index in range(partition):
        threads.append(partition_consumer.Consumer(index))

    for t in threads:
        t.start()

    time.sleep(50000)

if __name__ == '__main__':
    #logging.basicConfig(
    #    format='%(asctime)s.%(msecs)s:%(name)s:%(thread)d:%(levelname)s:%(process)d:%(message)s',
    #    level=logging.INFO
    #    )
    main()
