'use strict';

/**
 * You must return a date that comes in a predetermined number of seconds after 01.06.2020 00:00:002020
 * @param {number} seconds
 * @returns {Date}
 *
 * @example
 *      31536000 -> 01.06.2021
 *      0 -> 01.06.2020
 *      86400 -> 02.06.2020
 */
function secondsToDate(seconds) {
    let date = new Date(2020, 5, 1, 0, 0, 0);
    date.setSeconds(seconds);
    return date.toLocaleDateString("ru");
}

/**
 * You must create a function that returns a base 2 (binary) representation of a base 10 (decimal) string number
 * ! Numbers will always be below 1024 (not including 1024)
 * ! You are not able to use parseInt
 * @param {number} decimal
 * @return {string}
 *
 * @example
 *      5 -> "101"
 *      10 -> "1010"
 */
function toBase2Converter(decimal) {
    if (decimal < 1024) {
        return Number(decimal).toString(2);
    }
    return "Number should be less 1024!";
}

/**
 * You must create a function that takes two strings as arguments and returns the number of times the first string
 * is found in the text.
 * @param {string} substring
 * @param {string} text
 * @return {number}
 *
 * @example
 *      'a', 'test it' -> 0
 *      't', 'test it' -> 3
 *      'T', 'test it' -> 3
 */
function substringOccurrencesCounter(substring, text) {
    let count = 0;
    const textUp = text.toUpperCase();
    const substringUp = substring.toUpperCase();
    let pos = textUp.indexOf(substringUp);
    while (pos != -1) {
        count++;
        pos = textUp.indexOf(substringUp, pos + substring.length);
    }
    return count;
}

/**
 * You must create a function that takes a string and returns a string in which each character is repeated once.
 *
 * @param {string} string
 * @return {string}
 *
 * @example
 *      "Hello" -> "HHeelloo"
 *      "Hello world" -> "HHeello  wworrldd" // o, l is repeated more then once. Space was also repeated
 */
function repeatingLitters(string) {
    let result = "";
    for (let i = 0; i < string.length; i++) {
        let element = string[i];
        result += element;
        if (string.indexOf(element) == string.lastIndexOf(element)) {
            result += element;
        }
    }
    return result;
}

/**
 * You must write a function redundant that takes in a string str and returns a function that returns str.
 * ! Your function should return a function, not a string.
 *
 * @param {string} str
 * @return {function}
 *
 * @example
 *      const f1 = redundant("apple")
 *      f1() ➞ "apple"
 *
 *      const f2 = redundant("pear")
 *      f2() ➞ "pear"
 *
 *      const f3 = redundant("")
 *      f3() ➞ ""
 */
function redundant(str) {
    return function () {
        return str;
    };
}

/**
 * https://en.wikipedia.org/wiki/Tower_of_Hanoi
 *
 * @param {number} disks
 * @return {number}
 */
function towerHanoi(disks) {
    let count = 0;
    function stepsToSolve(disks, source, destination, buffer) {
        if (disks >= 1) {
            stepsToSolve(disks - 1, source, buffer, destination);
            let msg = `Move disk from tower ${source} to tower ${destination}`
            console.log(msg);
            stepsToSolve(disks - 1, buffer, destination, source);
            count++;
        }
        return;
    }
    stepsToSolve(disks, "A", "B", "C");
    return count;
}

/**
 * You must create a function that multiplies two matricies (n x n each).
 *
 * @param {array} matrix1
 * @param {array} matrix2
 * @return {array}
 *
 */
function matrixMultiplication(matrix1, matrix2) {
    let rowsM1 = matrix1.length;
    let colsM1 = matrix1[0].length;
    let rowsM2 = matrix2.length;
    let colsM2 = matrix2[0].length;
    let result = [];
    if (colsM1 != rowsM2) {
        return result;
    }
    for (let i = 0; i < rowsM1; i++) {
        result[i] = [];
    }
    for (let k = 0; k < colsM2; k++) {
        for (let i = 0; i < rowsM1; i++) {
            let t = 0;
            for (var j = 0; j < rowsM2; j++) {
                t += matrix1[i][j] * matrix2[j][k];
            }
            result[i][k] = t;
        }
    }
    return result;
}