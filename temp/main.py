from experta import *

class Plant(Fact):
    """Факт о растении."""
    pass

class PlantKnowledgeBase(KnowledgeEngine):
    """База знаний о растениях."""

    @DefFacts
    def load_facts(self):
        """Загружает факты о растениях."""
        yield Plant(name="Роза", color="красный", size="маленький", type="цветок", link="-")
        yield Plant(name="Либерти", color="зеленый", size="средний", type="кустарник", link="https://elovpark.ru/product/%d1%85%d0%be%d1%81%d1%82%d0%b0-%d0%bb%d0%b8%d0%b1%d0%b5%d1%80%d1%82%d0%b8/")
        yield Plant(name="Вербейник", color="желтый", size="средний", type="цветок", link="https://elovpark.ru/product/%d0%b2%d0%b5%d1%80%d0%b1%d0%b5%d0%b9%d0%bd%d0%b8%d0%ba-%d1%82%d0%be%d1%87%d0%b5%d1%87%d0%bd%d1%8b%d0%b9/")
        yield Plant(name="Тюльпан", color="желтый", size="средний", type="цветок", link="-")
        yield Plant(name="Барбарис", color="красный", size="большой", type="кустарник", link="https://elovpark.ru/product/%d0%b1%d0%b0%d1%80%d0%b1%d0%b0%d1%80%d0%b8%d1%81-%d1%82%d1%83%d0%bd%d0%b1%d0%b5%d1%80%d0%b3%d0%b0-%d0%b0%d1%82%d1%80%d0%be%d0%bf%d1%83%d1%80%d0%bf%d1%83%d1%80%d0%b5%d0%b0/")
        yield Plant(name="Бадан", color="розовый", size="маленький", type="цветок", link="https://elovpark.ru/product/%d0%b1%d0%b0%d0%b4%d0%b0%d0%bd-%d1%82%d0%be%d0%bb%d1%81%d1%82%d0%be%d0%bb%d0%b8%d1%81%d1%82%d0%bd%d1%8b%d0%b9/")
        yield Plant(name="Кактус", color="зеленый", size="маленький", type="цветок", link="-")
        yield Plant(name="Орхидея", color="белый", size="маленький", type="цветок", link="-")
        yield Plant(name="Медуница", color="синий", size="маленький", type="цветок", link="https://elovpark.ru/product/%d0%bc%d0%b5%d0%b4%d1%83%d0%bd%d0%b8%d1%86%d0%b0-%d1%81%d0%b0%d1%85%d0%b0%d1%80%d0%bd%d0%b0%d1%8f-%d0%bc%d0%b8%d1%81%d1%81%d0%b8%d1%81-%d0%bc%d1%83%d0%bd/")
        yield Plant(name="Пион", color="красный", size="маленький", type="цветок", link="https://elovpark.ru/product/%d0%bf%d0%b8%d0%be%d0%bd-%d1%82%d0%be%d0%bd%d0%ba%d0%be%d0%bb%d0%b8%d1%81%d1%82%d0%bd%d1%8b%d0%b9/")
        yield Plant(name="Ирис Вайт Ледис", color="белый", size="средний", type="цветок", link="https://elovpark.ru/product/%d0%b8%d1%80%d0%b8%d1%81-%d0%b2%d0%b0%d0%b9%d1%82-%d0%bb%d0%b5%d0%b4%d0%b8%d1%81/")
        yield Plant(name="Астра", color="красный", size="средний", type="цветок", link="-")
        yield Plant(name="Бегония", color="розовый", size="маленький", type="цветок", link="-")
        yield Plant(name="Каллы", color="белый", size="средний", type="цветок", link="-")
        yield Plant(name="Пальма", color="зеленый", size="большой", type="дерево", link="-")
        yield Plant(name="Нарцисс", color="желтый", size="маленький", type="цветок", link="-")
        yield Plant(name="Фиалка", color="синий", size="маленький", type="цветок", link="-")
        yield Plant(name="Гладиолус", color="красный", size="большой", type="цветок", link="-")
        yield Plant(name="Мирт", color="зеленый", size="маленький", type="цветок", link="-")
        yield Plant(name="Цинерария", color="синий", size="средний", type="цветок", link="-")
        yield Plant(name="Клематис", color="белый", size="большой", type="цветок", link="-")
        yield Plant(name="Лаванда", color="синий", size="средний", type="цветок", link="-")

    @Rule(Plant(color=MATCH.color))
    def find_by_color(self, color):
        """Находит растения по цвету."""
        print(f"Найдены растения с цветом '{color}':")
        for plant in self.facts.values():
            if isinstance(plant, Plant) and plant['color'] == color:
                print(f"- {plant['name']} ({plant['type']}, ссылка: {plant['link']})")

# Пример использования:
kb = PlantKnowledgeBase()
kb.reset()
kb.run()
kb.declare(Plant(color="красный"))