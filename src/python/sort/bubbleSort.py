# -- coding : utf-8 --
# @Time:2022/2/28 17:51
# @Author: Jianing Gou(goujianing19@mails.ucas.ac.cn)
"""
Main Idea: Iterate the array and swap the value if
               left value large than the right value. And
               do the iterate one by one. the time complecity
               is n^2.
"""


def bubbleSort(arr):
    n = len(arr)

    # Traverse through all array elements
    for i in range(n - 1):
        # range(n) also work but outer loop will
        # repeat one time more than needed.
        flag = False
        # Last i elements are already in place
        for j in range(0, n - i - 1):

            # traverse the array from 0 to n-i-1
            # Swap if the element found is greater
            # than the next element
            if arr[j] > arr[j + 1]:
                arr[j], arr[j + 1] = arr[j + 1], arr[j]
                flag = True

        if not flag:
            break


if __name__ == '__main__':
    array = [10, 80, 30, 90, 70, 100, 50, 70]
    print('Original array: ', array)

    bubbleSort(array)
    print('Sorted array: ', array)
