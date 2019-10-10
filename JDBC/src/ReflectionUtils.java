import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by  �߽���   2019/9/26 20:45
 * Description ����� Utils �������� �ṩ����˽�б���, ��ȡ�������� Class, ��ȡ������Ԫ�����Ե� Utils ����
 * Version 1.0
 */

public class ReflectionUtils {
    private static final String SETTER_PREFIX = "set";

    private static final String GETTER_PREFIX = "get";

    private static final String CGLIB_CLASS_SEPARATOR = "$$";

    /**
     * ͨ������, ��ö��� Class ʱ�����ĸ���ķ��Ͳ��������� ��: public EmployeeDao extends BaseDao<Employee, String>
     */
    @SuppressWarnings("unchecked")
    public static Class getSuperClassGenricType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }

        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }

    /**
     * ͨ������, ��� Class �����������ĸ���ķ��Ͳ������� ��: public EmployeeDao extends BaseDao<Employee, String>
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getSuperGenericType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * ѭ������ת��, ��ȡ����� DeclaredMethod
     */
    public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {

        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                // Method ���ڵ�ǰ�ඨ��, ��������ת��
            }
        }
        return null;
    }

    /**
     * ѭ������ת��, ��ȡ����� DeclaredField
     */
    public static Field getDeclaredField(Object object, String filedName) {

        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(filedName);
            } catch (NoSuchFieldException e) {
                // Field ���ڵ�ǰ�ඨ��, ��������ת��
            }
        }
        return null;
    }

    /**
     * ����Getter����.
     */
    public static Object invokeGetter(Object obj, String propertyName) {
        String getterMethodName = GETTER_PREFIX + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1, propertyName.length());
        return invokeMethod(obj, getterMethodName, new Class[]{}, new Object[]{});
    }

    /**
     * ����Setter����, ��ƥ�䷽������
     */
    public static void invokeSetter(Object obj, String propertyName, Object value) {
        String setterMethodName = SETTER_PREFIX + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1, propertyName.length());
        invokeMethodByName(obj, setterMethodName, new Object[]{value});
    }

    /**
     * ֱ�Ӷ�ȡ��������ֵ, ����private/protected���η�, ������getter����.
     */
    public static Object getFieldValue(final Object obj, final String fieldName) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        Object result = null;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ֱ�����ö�������ֵ, ����private/protected���η�, ������setter����.
     */
    public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * ֱ�ӵ��ö��󷽷�, ����private/protected���η�. ����һ���Ե��õ����������Ӧʹ��getAccessibleMethod()�������Method�󷴸�����. ͬʱƥ�䷽����+�������ͣ�
     */
    public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes, final Object[] args) {
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
        }

        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * ֱ�ӵ��ö��󷽷�, ����private/protected���η��� ����һ���Ե��õ����������Ӧʹ��getAccessibleMethodByName()�������Method�󷴸�����.
     * ֻƥ�亯����������ж��ͬ���������õ�һ����
     */
    public static Object invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
        Method method = getAccessibleMethodByName(obj, methodName);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
        }

        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * ѭ������ת��, ��ȡ�����DeclaredField, ��ǿ������Ϊ�ɷ���.
     * <p>
     * ������ת�͵�Object���޷��ҵ�, ����null.
     */
    public static Field getAccessibleField(final Object obj, final String fieldName) {
        if (obj == null || fieldName == null || "".equals(fieldName)) {
            return null;
        }
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {// NOSONAR
                // Field���ڵ�ǰ�ඨ��,��������ת��
            }
        }
        return null;
    }

    /**
     * ѭ������ת��, ��ȡ�����DeclaredField, ��ǿ������Ϊ�ɷ���.
     * <p>
     * ������ת�͵�Object���޷��ҵ�, ����null.
     *
     * @param <T>
     */
    public static <T> Field getField(final Class<T> clazz, final String fieldName) {
        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                return field;
            } catch (NoSuchFieldException e) {// NOSONAR
                // Field���ڵ�ǰ�ඨ��,��������ת��
            }
        }
        return null;
    }

    /**
     * ѭ������ת��, ��ȡ�����DeclaredMethod,��ǿ������Ϊ�ɷ���. ������ת�͵�Object���޷��ҵ�, ����null. ƥ�亯����+�������͡�
     * <p>
     * ���ڷ�����Ҫ����ε��õ����. ��ʹ�ñ�������ȡ��Method,Ȼ�����Method.invoke(Object obj, Object... args)
     */
    public static Method getAccessibleMethod(final Object obj, final String methodName, final Class<?>... parameterTypes) {
        if (obj == null || methodName == null || "".equals(methodName)) {
            return null;
        }

        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
            try {
                Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException e) {
                // Method���ڵ�ǰ�ඨ��,��������ת��
            }
        }
        return null;
    }

    /**
     * ѭ������ת��, ��ȡ�����DeclaredMethod,��ǿ������Ϊ�ɷ���. ������ת�͵�Object���޷��ҵ�, ����null. ֻƥ�亯������
     * <p>
     * ���ڷ�����Ҫ����ε��õ����. ��ʹ�ñ�������ȡ��Method,Ȼ�����Method.invoke(Object obj, Object... args)
     */
    public static Method getAccessibleMethodByName(final Object obj, final String methodName) {
        if (obj == null || methodName == null || "".equals(methodName)) {
            return null;
        }
        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    makeAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * �ı�private/protected�ķ���Ϊpublic������������ʵ�ʸĶ�����䣬����JDK��SecurityManager��Թ��
     */
    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    /**
     * �ı�private/protected�ĳ�Ա����Ϊpublic������������ʵ�ʸĶ�����䣬����JDK��SecurityManager��Թ��
     */
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers()))
                && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    /**
     * ͨ������, ���Class�����������ķ��Ͳ���������, ע�ⷺ�ͱ��붨���ڸ��ദ ���޷��ҵ�, ����Object.class. eg. public UserDao extends HibernateDao<User>
     */
    public static <T> Class<T> getClassGenricType(final Class clazz) {
        return getClassGenricType(clazz, 0);
    }

    /**
     * ͨ������, ���Class�����������ĸ���ķ��Ͳ���������. ���޷��ҵ�, ����Object.class.
     * <p>
     * ��public UserDao extends HibernateDao<User,Long>
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be determined
     */
    public static Class getClassGenricType(final Class clazz, final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if ((index >= params.length) || (index < 0)) {

            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }

    public static Class<?> getUserClass(Object instance) {
        if (instance == null) {
            throw new NullPointerException();
        }
        Class clazz = instance.getClass();
        if ((clazz != null) && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
            Class<?> superClass = clazz.getSuperclass();
            if ((superClass != null) && !Object.class.equals(superClass)) {
                return superClass;
            }
        }
        return clazz;

    }

    /**
     * ������ʱ��checked exceptionת��Ϊunchecked exception.
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
        if ((e instanceof IllegalAccessException) || (e instanceof IllegalArgumentException) || (e instanceof NoSuchMethodException)) {
            return new IllegalArgumentException(e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException(((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("Unexpected Checked Exception.", e);
    }

}

