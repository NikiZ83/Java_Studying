#!/bin/bash

if [ -z "$1" ]; then
	echo "Использование: ./run.sh Main.java"
	exit 1
fi

filename = $(basename -- "$1")
classname = "${filename%.*}"

java "$1"

if [ $? -eq 0 ]; then
	echo "Компиляция успешна! Запускаем $classname..."
	java "classname"
else
	echo "ошибка компиляции!"
fi
