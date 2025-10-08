// Абстрактный класс Furnuture
abstract class Furniture {
    protected String material;
    protected String color;
    protected double price;

    public Furniture() {
        this.material = "Неизвестный";
        this.color = "Без цвета";
        this.price = 0.0;
    }

    public Furniture(String material, String color, double price) {
        this.material = material;
        this.color = color;
        this.price = price;
    }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public abstract void showInfo();
    public abstract void use();
}

// Класс наследник Table
class Table extends Furniture {
    private int legs;
    private static int count = 0; // статический счетчик

    public Table() {
        super();
        this.legs = 4;
        count++;
    }

    public Table(String material, String color, double price, int legs) {
        super(material, color, price);
        this.legs = legs;
        count++;
    }

    public int getLegs() { return legs; }
    public void setLegs(int legs) { this.legs = legs; }

    @Override
    public void showInfo() {
        System.out.println("Стол: материал=" + material + ", цвет=" + color +
                           ", цена=" + price + ", ножек=" + legs);
    }

    @Override
    public void use() {
        System.out.println("Стол используется для работы или еды.");
    }

    public static int getCount() {
        return count;
    }
}

class Chair extends Furniture {
    private boolean hasBack;
    private double height;

    public Chair() {
        super();
        this.hasBack = true;
        this.height = 45.0;
    }

    public Chair(String material, String color, double price, boolean hasBack, double height) {
        super(material, color, price);
        this.hasBack = hasBack;
        this.height = height;
    }

    public boolean isHasBack() { return hasBack; }
    public void setHasBack(boolean hasBack) { this.hasBack = hasBack; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    @Override
    public void showInfo() {
        System.out.println("Стул: материал=" + material + ", цвет=" + color +
                           ", цена=" + price + ", спинка=" + hasBack + ", высота=" + height);
    }

    @Override
    public void use() {
        System.out.println("Стул используется для сидения.");
    }
}

//Класс наследник первого уровня
class Bed extends Furniture {
    private String size;
    private boolean hasStorage;

    public Bed() {
        super();
        this.size = "Односпальная";
        this.hasStorage = false;
    }

    public Bed(String material, String color, double price, String size, boolean hasStorage) {
        super(material, color, price);
        this.size = size;
        this.hasStorage = hasStorage;
    }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public boolean isHasStorage() { return hasStorage; }
    public void setHasStorage(boolean hasStorage) { this.hasStorage = hasStorage; }

    @Override
    public void showInfo() {
        System.out.println("Кровать: материал=" + material + ", цвет=" + color +
                           ", цена=" + price + ", размер=" + size + ", ящик для хранения=" + hasStorage);
    }

    @Override
    public void use() {
        System.out.println("Кровать используется для сна и отдыха.");
    }
}

//Наследуемый класс второго уровня
class OfficeChair extends Chair {
    private boolean hasWheels;   // есть ли колёсики
    private boolean isAdjustable; // регулируется ли по высоте

    public OfficeChair() {
        super();
        this.hasWheels = true;
        this.isAdjustable = true;
    }

    public OfficeChair(String material, String color, double price,
                       boolean hasBack, double height,
                       boolean hasWheels, boolean isAdjustable) {
        super(material, color, price, hasBack, height);
        this.hasWheels = hasWheels;
        this.isAdjustable = isAdjustable;
    }

    public boolean isHasWheels() { return hasWheels; }
    public void setHasWheels(boolean hasWheels) { this.hasWheels = hasWheels; }

    public boolean isAdjustable() { return isAdjustable; }
    public void setAdjustable(boolean adjustable) { isAdjustable = adjustable; }

    @Override
    public void showInfo() {
        System.out.println("Офисный стул: материал=" + material +
                           ", цвет=" + color +
                           ", цена=" + price +
                           ", спинка=" + isHasBack() +
                           ", высота=" + getHeight() +
                           ", колёсики=" + hasWheels +
                           ", регулируемый=" + isAdjustable);
    }

    @Override
    public void use() {
        System.out.println("Офисный стул используется для работы за компьютером.");
    }
}


// ================== Главный класс ==================
public class Main {
    public static void main(String[] args) {
        Table t1 = new Table("Дерево", "Коричневый", 5000, 4);
        Table t2 = new Table("Металл", "Черный", 7000, 3);
        Chair c1 = new Chair("Пластик", "Белый", 1200, true, 40);
        Bed b1 = new Bed("Дерево", "Светлый", 15000, "Двуспальная", true);
        OfficeChair oc = new OfficeChair("Кожзам", "Черный", 8000,
                true, 45,
                true, true);
        
        Furniture obj1 = new Table();
        System.out.print(obj1.getMaterial());
        System.out.println();
        
        t1.showInfo();
        t1.use();
        System.out.println();

        t2.showInfo();
        t2.use();
        System.out.println();

        c1.showInfo();
        c1.use();
        System.out.println();

        b1.showInfo();
        b1.use();
        System.out.println();
        
        oc.showInfo();
        oc.use();

        System.out.println("Количество созданных столов: " + Table.getCount());
    }
}
