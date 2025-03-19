-- Водители
CREATE TABLE driver (
	driver_id SERIAL PRIMARY KEY,
	name VARCHAR(500) NOT NULL,
	age INT  NOT NULL CHECK (age >= 18),
	has_driver_license BOOLEAN NOT NULL DEFAULT TRUE
);

-- Автомобили
CREATE TABLE car (
	car_id SERIAL PRIMARY KEY,
	brand VARCHAR(500) NOT NULL,
	model VARCHAR(500),
	price NUMERIC(15, 2)  NOT NULL CHECK (price > 0) 
);


-- Какие автомобили может водить каджый водитель?
CREATE TABLE car_driver (
	car_driver_id SERIAL PRIMARY KEY,
	car_id INT NOT NULL,
	driver_id INT NOT NULL
);


-- Устанавливаем связи между таблицами
ALTER TABLE car_driver ADD FOREIGN KEY (car_id) REFERENCES car(car_id);
ALTER TABLE car_driver ADD FOREIGN KEY (driver_id) REFERENCES driver(driver_id);

