-- Ограничиваем минимальный предел возраста студента
ALTER TABLE student ADD CONSTRAINT student_age CHECK (age >= 16);

-- Добавляем конроль заполнения имен студентов
ALTER TABLE student ALTER COLUMN name SET NOT NULL;


-- Добавляем уникальность для имен студентов
ALTER TABLE student ADD CONSTRAINT name_unique UNIQUE(name);

-- Устанавливаем для возраста студента значение по умолчанию 20
ALTER TABLE student ALTER COLUMN age SET DEFAULT 20;
