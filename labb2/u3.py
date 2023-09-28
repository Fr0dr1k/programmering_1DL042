import random
import time
import matplotlib.pyplot as plt
import numpy as np
import sys


def switch_place(num_list):
    hase_swapped = False
    for i in range(len(num_list) - 1):
        if num_list[i] > num_list[i + 1]:
            hase_swapped = True
            temp_num = num_list[i]
            num_list[i] = num_list[i + 1]
            num_list[i + 1] = temp_num
    return hase_swapped


def bubblesort(num_list):
    while switch_place(num_list):
        pass
    return num_list


def testing_testing(testing_list):
    for i in testing_list:
        print("Unsorted list: ", i)
        bubblesort(i)
        print("Sorted list: ", i)


def higher_grade_bubblesort():
    list_max_size = 5000
    list_size = 100
    sort_data = []
    bubblesort_data = []
    while list_size <= list_max_size:
        random_num_list = [random.randint(0, 100000) for _ in range(list_size)]
        start_time = time.time()
        bubblesort(random_num_list.copy())
        bubblesort_data.append([list_size, time.time() - start_time])
        print("It took " + str(time.time() - start_time) + "s to sort a " + str(list_size) +
              " long list the list using bubblesort")

        start_time = time.time()
        random_num_list.sort()
        sort_data.append([list_size, time.time() - start_time])
        print("It took " + str(time.time() - start_time) + "s to sort a " + str(list_size) +
              " long list the list using sort()")

        list_size += 100


def for_fun():
    print("starting")
    list_max_size = 100000
    sort_data, bubblesort_data = [[], []], [[], []]
    list_size_jump = 100

    list_size = 0
    execution_time_bubblesort, execution_time_sort = 0, 0
    while execution_time_bubblesort < 10.0 and execution_time_sort < 10.0 and list_size < list_max_size:
        random_num_list = [random.randint(0, 100000) for _ in range(list_size)]

        start_time = time.time()
        bubblesort(random_num_list.copy())
        execution_time_bubblesort = time.time() - start_time
        bubblesort_data[1].append(execution_time_bubblesort)
        bubblesort_data[0].append(list_size)

        start_time = time.time()
        random_num_list.copy().sort()
        execution_time_sort = time.time() - start_time
        sort_data[1].append(execution_time_sort)
        sort_data[0].append(list_size)

        list_size += 100
        if list_size % 1000 == 0:
            print("List size : ", list_size, " Bubblesort time : ", execution_time_bubblesort, " Sort time : ", execution_time_sort)

    np.save("sort_data", np.array(sort_data))
    np.save("bubblesort_data", np.array(bubblesort_data))
    print("done")


def read_file():
    file_bubblesort = np.load("bubblesort_data.npy")
    file_sort = np.load("sort_data.npy")

    print(file_sort.tolist())

    plt.plot(file_bubblesort.tolist()[0], file_bubblesort.tolist()[1])

    plt.plot(file_sort.tolist()[0], file_sort.tolist()[1])

    plt.xlabel("List size")
    plt.ylabel("Time to sort")
    plt.legend({"Sort        ", "Bubblesort"})
    plt.show()



if __name__ == '__main__':
    # print(bubblesort([1, 3, 2, 43, 1, 431, 312, 4142, 3, 1]))
    # higher_grade_bubblesort()
    testing_testing([[1, 3, 2, 3, 1, 2], [32, 31, 4, 1, 3123, 3], [], [1]])

    #for_fun()
    #read_file()
