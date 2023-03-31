package com.example.lab82;

import com.example.lab82.utils.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class Application {
	private static ApplicationContext applicationContext;

	public static void main(String[] args) {
		applicationContext = SpringApplication.run(Application.class, args);

		for(int i = 0; i < 100; i++)
			try {
				handleUserInput();
			} catch (RuntimeException e) {}
	}

	public static void handleUserInput() {
		Map<String, MyUtil> beans = applicationContext.getBeansOfType(MyUtil.class);

		Scanner scanner = new Scanner(System.in);
		System.out.print("Введите путь к файлу: ");
		String path = scanner.nextLine();

		String beanName = "";
		List<String> methodList = new ArrayList<>();
		for (Map.Entry<String, MyUtil> bean : beans.entrySet()) {
			if(bean.getValue().isSupporting(path)) {
				beanName = bean.getKey();
				for(Method method : bean.getValue().getClass().getDeclaredMethods())
					if(method.getName().startsWith("print")) methodList.add(method.getName());
			}
		}

		if(beanName.equals("")) throw new RuntimeException();

		System.out.println("Available methods: ");
		for (int i = 1; i <= methodList.size(); i++) {
			System.out.println(i + ") " + methodList.get(i - 1));
		}

		int key = 0;
		do {
			String key_str = scanner.nextLine();
			try {
				key = Integer.parseInt(key_str);
			} catch (NumberFormatException e) { e.printStackTrace(); }
		} while(key < 1 || key > methodList.size());

		try {
			applicationContext
					.getBean(beanName, MyUtil.class)
					.getClass()
					.getDeclaredMethod(methodList.get(key - 1), String.class)
					.invoke(null, path);
		} catch (Exception e) { e.printStackTrace(); }

	}

}

/*
Задание:
Сделать модульное консольное приложение на Spring Boot, которое принимает в качестве аргумента командной строки имя файла,
ищет среди подключенных модулей те, которые поддерживают формат заданного файла, и предлагает пользователю выбрать функцию для обработки файла.
Приложение должно использовать аннотации и Classpath scanning для определения доступных модулей.
Приложение не должно использовать xml для конфигурирования приложения.
Каждый модуль должен иметь методы:
Проверка, поддерживает ли модуль формат файла
Описание функции
Собственно, функция
Примеры модулей:

текстовый файл - подсчет и вывод количества строк
текстовый файл - вывод частоты вхождения каждого символа
текстовый файл - придумайте собственную функцию

изображение - вывод размера изображения
изображение - вывод информации exif (можно воспользоваться библиотекой metadata-extractor)
изображение - придумайте собственную функцию

mp3 - вывод названия трека из тегов (можно воспользоваться утилитой ffmpeg)
mp3 - вывод длительности в секундах
mp3 - придумайте собственную функцию

каталог - вывод списка файлов в каталоге
каталог - подсчет размера всех файлов в каталоге
каталог - придумайте собственную функцию*/