# -- coding : utf-8 --
# @Time:2022/3/9 20:56
# @Author: Jianing Gou(goujianing19@mails.ucas.ac.cn)
from numpy import array


def mergeSortHelper(arr, copyBuffer, low, high):
    if low < high:
        middle = (low + high) // 2
        mergeSortHelper(arr, copyBuffer, low, middle)
        mergeSortHelper(arr, copyBuffer, middle + 1, high)
        merge(arr, copyBuffer, low, middle, high)
    else:
        return


def merge(arr, copyBuffer, low, middle, high):
    i1 = low

    i2 = middle + 1
    for i in range(low, high + 1):
        if i1 > middle:
            copyBuffer[i] = arr[i2]
        elif i2 > high:
            copyBuffer[i] = arr[i1]
        elif arr[i1] < arr[i2]:
            copyBuffer[i] = arr[i1]
            i1 += 1
        else:
            copyBuffer[i] = arr[i2]
    for i in range(low, high + 1):
        arr[i] = copyBuffer[i]


def mergeSort(arr: list):
    copyBuffer = arr
    mergeSortHelper(arr, copyBuffer, 0, len(arr) - 1)


if __name__ == '__main__':
    array = [10, 80, 30, 90, 70, 100, 50, 70]
    print('Original array: ', array)

    mergeSort(array)
    print('Sorted array: ', array)
