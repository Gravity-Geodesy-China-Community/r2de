# -- coding : utf-8 --
# @Time:2022/3/9 20:15
# @Author: Jianing Gou(goujianing19@mails.ucas.ac.cn)
"""
this function is to find the k-th most value in a array;
"""

import sort.quickSort as qs


def find_kth(arr: list, k: int):
    if len(arr) < k:
        return -1
    q = qs.partition(arr, 0, len(arr) - 1)
    while q != k - 1:
        if q < k - 1:
            q = qs.partition(arr, q + 1, len(arr) - 1)
        else:
            q = qs.partition(arr, 0, q - 1)
    return arr[q]


if __name__ == '__main__':
    array = [10, 80, 30, 90, 70, 100, 50, 70]
    print('Original array: ', array)

    kth = find_kth(array, 5)
    print('The k-th smallest: ', kth)

