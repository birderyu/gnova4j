package gnova.query.linq;

import gnova.query.cursor.*;
import gnova.core.function.Action;
import gnova.core.function.ListBuilder;

import java.util.*;
import java.util.function.*;

/**
 * 一个简单的LINQ实现
 *
 * @param <E>
 * @param <R>
 */
public class SimpleLinq<E, R> implements Linq<E, R> {

    public static <E, R> SimpleLinq<E, R> Select(Function<E, R> selector) {
        SimpleLinq<E, R> sl = new SimpleLinq<>();
        sl.select(selector);
        return sl;
    }

    public static <E> SimpleLinq<E, E> Select(Class<? extends E> clazz) {
        return Select(Function.identity());
    }

    private Function<? super E, ? extends R> selector;
    private Cursorable<? extends E> source;
    private List<Predicate<? super E>> predicates;

    @Override
    public Linq<E, R> select(Function<? super E, ? extends R> selector) {
        this.selector = selector;
        return this;
    }

    @Override
    public Linq<E, R> from(Cursorable<? extends E> source) {
        this.source = source;
        return this;
    }

    @Override
    public Linq<E, R> from(Iterable<? extends E> source) {
        if (source instanceof Cursorable) {
            this.source = (Cursorable<? extends E>) source;
        } else if (source instanceof AutoCloseable) {
            this.source = new IterableCursorable<>(source, (Action) () -> {
                try {
                    ((AutoCloseable) source).close();
                } catch (Exception e) {
                    // do nothing
                }
            });
        } else {
            this.source = new IterableCursorable<>(source);
        }
        return this;
    }

    @Override
    public Linq<E, R> where(Predicate<? super E> predicate) {
        if (predicate == null) {
            return this;
        }
        if (predicates == null) {
            predicates = new ArrayList<>();
        }
        predicates.add(predicate);
        return this;
    }

    @Override
    public <T> Grouping<T, E, Linq<E, R>> groupBy(Function<E, T> selector) {
        // TODO
        return null;
    }

    @Override
    public Cursor<R> cursor() {

        if (source == null) {
            // 缺少from语句，返回一个空的游标
            return new EmptyCursor<>();
        }

        // 处理source
        Cursor<E> cursor = new SuperCursor<>(source.cursor());

        // 处理where
        if (predicates != null && !predicates.isEmpty()) {
            for (Predicate<? super E> predicate : predicates) {
                cursor = new PredicateCursor<>(cursor, predicate);
            }
        }

        // 处理select
        if (selector == null) {
            // 没有select语句，直接强转
            return new ConvertCursor<>(cursor, e -> (R) e);
        } else {
            return new ConvertCursor<>(cursor, selector);
        }
    }

    public static class Student {
        public int id;
        public String name;
        public int age;
        public Student(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }

    public static void main(String[] args) {

        List<Student> students = ((ListBuilder<Student>) () -> new ArrayList<>())
                .build(new Student(1, "Tony", 25),
                        new Student(2, "Steven", 70),
                        new Student(3, "Thor", 5000),
                        new Student(4, "Hulk", 30),
                        new Student(5, "Petty", 15),
                        new Student(6, "Loki", 4500),
                        new Student(7, "Natasha", 17),
                        new Student(8, "Parker", 15));

        // 1 构建一个LINQ
        Linq<Student, String> linq = Select((Function<Student, String>) student -> student.name)
                .from(() -> new IteratorCursor<>(students.iterator()))
                .where(student -> student.age < 18);
        System.out.println("State 1: ");
        linq.forEach(s -> {
            System.out.println(s);
        });

        // 2 向集合中继续添加数据
        students.add(new Student(9, "Li Lei", 12));
        students.add(new Student(10, "Han Meimei", 11));
        System.out.println("State 2: ");
        linq.forEach(s -> {
            System.out.println(s);
        });

        // 3 增加查询条件
        linq.where(student -> student.id <= 9);
        System.out.println("State 3: ");
        linq.forEach(s -> {
            System.out.println(s);
        });

        int stop = 1;
        stop++;

        for (String name :
                Select((Function<Student, String>) student -> student.name)
                .from(() -> new IteratorCursor<>(students.iterator()))
                .where(student -> student.age < 18)) {
            System.out.println(name);
        }

        int groupId = 0;
        for (Linq<Student, String> ll : Select((Function<Student, String>) student -> student.name)
                .from(() -> new IteratorCursor<>(students.iterator()))
                .where(student -> student.age < 18)
                .groupBy(student -> student.age)) {
            System.out.println("groupId: " + (++groupId));
            for (String name : ll) {
                System.out.println(name);
            }
        }



    }
}