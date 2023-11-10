package org.jmhsrobotics.offseason2023.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.jmhsrobotics.offseason2023.subsystems.drive.DriveSubsystem;
import org.jmhsrobotics.warcore.math.TuneableProfiledPIDController;

import com.fasterxml.jackson.databind.introspect.AccessorNamingStrategy.Provider;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Jank {
    public static class toSend implements Sendable {

        private Field f;
        private Object obj;
        private ArrayList<TuneableField> val;

        public toSend(ArrayList<TuneableField> val) {
            this.val = val;
        }

        private void addtoBuilder(SendableBuilder builder, TuneableField tf) {
            try {
                for (var methods : builder.getClass().getMethods()) {
                    if (methods.getParameterCount() == 3 && methods.getParameterTypes()[0] == String.class
                            && methods.getParameterTypes()[1] == Supplier.class
                            && methods.getParameterTypes()[2] == Consumer.class) {
                        System.out.println(
                                methods.getName() + " _X_ "
                                        + methods.getParameters()[1].getParameterizedType().getTypeName());
                        Type providerType = ((ParameterizedType) methods.getParameters()[1].getParameterizedType())
                                .getActualTypeArguments()[0];
                        Type consumerType = ((ParameterizedType) methods.getParameters()[1].getParameterizedType())
                                .getActualTypeArguments()[0];
                        // for( var paramtype : todo.getParameterTypes()){
                        // System.out.println(providerType == Double.class);
                        Supplier<?> supplier = () -> {
                            // var tunvaltmp = val.get(0);
                            try {
                                System.out.println("Test.");
                                var tmp2 = tf.f.get(tf.obj);
                                // System.out.println(providerType.getClass().getName());
                                return tf.f.getType().cast(tmp2);
                            } catch (IllegalArgumentException | IllegalAccessException e) {
                                e.printStackTrace();
                                System.out.println("Test3");
                                return null;
                                // e.printStackTrace();
                            }
                        };
                        Consumer<?> consumer = (value) -> {
                            try {
                                tf.f.set(tf.obj, value);
                                System.out.println("Setting!!!");
                            } catch (IllegalArgumentException | IllegalAccessException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        };
                        // consumer.class.
                        System.out.println(providerType + " != " + tf.f.getType() );
                        if(providerType != tf.f.getType()){
                            continue;
                        }
                        System.out.println("Attempting: " + tf.f.getName());
                        methods.invoke(builder, tf.f.getName(), methods.getParameters()[1].getType().cast(supplier), null);
                        // builder.addIntegerArrayProperty("asdf",supplier,null);
                        // SmartDashboard.putData((Sendable)out);
                        // builder.addIntegerArrayProperty(null, null, null);
                        // System.out.println("\t " + paramtype.getName());
                        // Supplier<providerType> t;
                        // }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void initSendable(SendableBuilder builder) {
            try {

                builder.setSmartDashboardType("tunableVal");
                builder.addIntegerArrayProperty("asdf", ()->{ return new long[]{1,2,3};}, ( t) -> {System.out.println("Setting!!3!");});
                // Supplier<?> supplier1 = () -> {
                //     var tunvaltmp = val.get(0);
                //     return null;
                // };
                // Consumer<?> consumer2 = (value) -> {
                // };
                // for (var todo : builder.getClass().getMethods()) {
                //     if (todo.getParameterCount() == 3 && todo.getParameterTypes()[0] == String.class
                //             && todo.getParameterTypes()[1] == Supplier.class
                //             && todo.getParameterTypes()[2] == Consumer.class
                //             && todo.getName().equals("addIntegerArrayProperty")) {
                //         System.out.println(
                //                 todo.getName() + " " + todo.getParameters()[1].getParameterizedType().getTypeName());
                //         Type providerType = ((ParameterizedType) todo.getParameters()[1].getParameterizedType())
                //                 .getActualTypeArguments()[0];
                //         Type consumerType = ((ParameterizedType) todo.getParameters()[1].getParameterizedType())
                //                 .getActualTypeArguments()[0];
                //         // for( var paramtype : todo.getParameterTypes()){
                //         // System.out.println(providerType == Double.class);
                //         Supplier<?> supplier = () -> {
                //             // var tunvaltmp = val.get(0);
                //             return new long[] { 2, 5 };
                //         };
                //         Consumer<?> consumer = (value) -> {
                //             System.out.println(value);
                //         };
                //         todo.invoke(builder, "memesadsf", supplier, consumer);
                //         // builder.addIntegerArrayProperty(null, null, null);
                //         // System.out.println("\t " + paramtype.getName());
                //         // Supplier<providerType> t;
                //         // }
                //     }

                // }
                for (TuneableField tf : val) {
                    var type = tf.f.getType();
                    Object val = tf.f.get(tf.obj);
                    addtoBuilder(builder, tf);
                    // if (val instanceof Double || val instanceof Integer) {
                    //     builder.addDoubleProperty(tf.f.getName(), () -> {
                    //         double tmp = Double.NaN;
                    //         // tf.f.get
                    //         try {
                    //             // Double.
                    //             tmp = ((Number) tf.f.get(tf.obj)).doubleValue();

                    //         } catch (IllegalArgumentException | IllegalAccessException e) {
                    //             e.printStackTrace();
                    //         }
                    //         return tmp;
                    //     }, (double toset) -> {
                    //         // try {
                    //         // tf.f.set(tf.obj, type.cast(toset));
                    //         // } catch (IllegalArgumentException | IllegalAccessException e) {
                    //         // // TODO Auto-generated catch block
                    //         // e.printStackTrace();
                    //         // }
                    //     });
                    // } if (val instanceof long[]){

                    // }
                    // if (type == double.class){

                    // }
                    // // tf.f.getType()
                    // switch (tf.f.getType().) {
                    // default:

                    // }
                }
                // NetworkTableEntry.isValidDataType();
                // builder.addIntegerProperty(null, null, null);
                // builder.addDoubleProperty(f.getName(), () -> {
                // try {
                // return f.getDouble(obj);
                // } catch (Exception e) {
                // return Double.NaN;
                // }
                // }, (double val) -> {
                // try {
                // f.setDouble(this.obj, val);
                // } catch (IllegalArgumentException | IllegalAccessException e) {
                // // TODO Auto-generated catch block
                // e.printStackTrace();
                // }
                // });
            } catch (Exception e) {
                e.printStackTrace();
            }
            // TODO Auto-generated method stub
        }

    }

    public static void jank(Object data) {
        ArrayList<TuneableField> foundFields = new ArrayList<TuneableField>();
        try {
            for (Field f : data.getClass().getDeclaredFields()) {

                // Check for Tunable Annotation in Field
                if (f.getAnnotation(Tunable.class) != null) {
                    f.setAccessible(true);
                    foundFields.add(new TuneableField(f, data));

                    // System.out.println(data.getClass().getPackageName() + " - " + f.getName() + "
                    // -- To be Tunable...");
                    // System.out.println(f.get(data));
                    // // System.out.println();
                    // f.set(data, Double.valueOf(6));
                    // System.out.println(f.get(data));
                }

                // Check if Class of field is Tunable
                // Recurse if so.
                if (f.getType().getAnnotation(Tunable.class) != null) {
                    System.out.println("Enter  ->" + f.getType().getName());
                    f.setAccessible(true);
                    Jank.jank(f.get(data));
                }

                // System.out.println(f.getName());
                // var anno = f.getAnnotation(Tunable.class);
                // // DriveSubsystem.class.getPackageName()
                // if (anno != null) {
                // System.out.println("Anno Found" + f.getName());
                // f.setAccessible(true);
                // var val = f.getDouble(data);
                // System.out.println("Data: " + val);
                // var out = new toSend(f, data);
                // SmartDashboard.putData("testing", out);
                // }
            }
            var sendable = new toSend(foundFields);
            SmartDashboard.putData("Testing", sendable);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public static void initTune(RobotBase base) {
        Jank.jank(base);
    }

    private static class TuneableField {
        public final Field f;
        public final Object obj;

        public TuneableField(Field f, Object obj) {
            this.f = f;
            this.obj = obj;
        }
    }
    // private static Field[] getAllFields(){

    // }

}
