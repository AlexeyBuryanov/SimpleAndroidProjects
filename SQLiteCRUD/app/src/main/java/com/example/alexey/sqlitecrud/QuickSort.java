package com.example.alexey.sqlitecrud;

/**
 * Created by Alexey on 01.02.2018.
 * Быстрая сортировка.
 */
class QuickSort {

    private void swap(String[] array, int i, int j) {
        String tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    } // Swap

    /**
     * Эта функция принимает последний элемент в качестве pivot, помещает элемент
     * pivot в правильное положение в отсортированном массиве и помещает все меньшие
     * (меньше, чем pivot) влево от pivot и все более крупные элементы вправо от pivot
     * */
    private int partition(String arr[], int low, int high, ESort direction) {
        String pivot = arr[high];
        int i = (low - 1); // индекс меньшего элемента
        if (direction == ESort.ASCENDING) {
            for (int j = low; j < high; j++) {
                // Если текущий элемент меньше или равен pivot
                if (arr[j].compareTo(pivot) <= 0) {
                    i++;
                    // Замена arr[i] and arr[j]
                    swap(arr, i, j);
                } // if
            } // for j
        } else {
            for (int j = low; j < high; j++) {
                // Если текущий элемент меньше или равен pivot
                if (arr[j].compareTo(pivot) >= 0) {
                    i++;
                    // Замена arr[i] and arr[j]
                    swap(arr, i, j);
                } // if
            } // for j
        } // if-else

        // Замена arr[i+1] и arr[high] (или pivot)
        swap(arr, i+1, high);
        return i+1;
    } // partition


    /**
     * Основная функция, реализующая QuickSort.
     * @param arr Массив для сортировки
     * @param low Начальный индекс
     * @param high Конечный индекс
     * */
    void sort(String arr[], int low, int high, ESort direction) {
        if (low < high) {
            /* pi - индекс разбиения, arr[pi] теперь находится в нужном месте */
            int pi = partition(arr, low, high, direction);

            // Рекурсивно сортировать элементы перед разделом и после раздела
            sort(arr, low, pi-1, direction);
            sort(arr, pi+1, high, direction);
        } // if
    } // sort
} // QuickSort