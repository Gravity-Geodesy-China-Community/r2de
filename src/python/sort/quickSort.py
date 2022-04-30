# -- coding : utf-8 --
# @Time:2022/2/28 17:51
# @Author: Jianing Gou(goujianing19@mails.ucas.ac.cn)

"""Python3 implementation of QuickSort
Main idea:
1、find a pivot(Last number in a array);
2、partition, split the original array into two subarray, one is the number below the pivot,
one is over the pivot;
3、recursive the QuickSort algorithm
"""


def partition(arraylist, begin, end):
    # the aim of this method is to partition the array into two subarray that satisfy constrains above
    pivot_index = end
    pivot = arraylist[end]
    # double pointer method to traverse the array
    while begin < end:
        # to find and swap number below and upper the pivot
        while begin < len(arraylist) and arraylist[begin] < pivot:
            # when break this statement means there is a num
            # that bigger than pivot
            begin += 1
        while arraylist[end] >= pivot:
            # when break this statement means there is a num smaller than pivot
            end -= 1
        # swap value if we do not traverse the array completely
        if begin < end:
            arraylist[begin], arraylist[end] = arraylist[end], arraylist[begin]
        # put the pivot number in right place and return the new index
    arraylist[pivot_index], arraylist[begin] = arraylist[begin], arraylist[pivot_index]
    return begin


def quickSort(arraylist, begin, end):
    # always define the last number as the pivot：
    if begin < end:
        p = partition(arraylist, begin, end)  # return new pivot index and partition the array in two subarray
        # recursive
        quickSort(arraylist, begin, p - 1)  # left subarray equal to number lower than pivot
        quickSort(arraylist, p + 1, end)  # right subarray equal to number upper than pivot
    # Recursive ends when begin >= end means they are only one or two number in each subarray


if __name__ == '__main__':
    array = [10, 80, 30, 90, 70, 100, 50, 70]
    print('Original array: ', array)

    quickSort(array, 0, len(array) - 1)
    print('Sorted array: ', array)
