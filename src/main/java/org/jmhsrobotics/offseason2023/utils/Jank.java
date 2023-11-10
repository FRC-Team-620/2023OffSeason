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

        private ArrayList<TuneableField> val;

        public toSend(ArrayList<TuneableField> val) {
            this.val = val;
        }

        private void addtoBuilder(SendableBuilder builder, TuneableField tf) {
            try {
                var obj = tf.obj;
                var f = tf.f;
                var key = f.getName();
                if (f.getType() == boolean.class) {
                    builder.addBooleanProperty(key, () -> { // Getter
                        try {
                            return f.getBoolean(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return false;
                        }
                    }, (boolean value) -> { // Setter
                        try {
                            f.setBoolean(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (f.getType() == boolean[].class) {
                    builder.addBooleanArrayProperty(key, () -> { // Getter
                        try {
                            return (boolean[]) f.get(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }, (boolean[] value) -> { // Setter
                        try {
                            f.set(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (f.getType() == long.class) {
                    builder.addIntegerProperty(key, () -> { // Getter
                        try {
                            
                            return f.getLong(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return 0;
                        }
                    }, (long value) -> { // Setter
                        try {
                            f.setLong(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (f.getType() == long[].class) {
                    builder.addIntegerArrayProperty(key, () -> { // Getter
                        try {
                            return (long[]) f.get(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }, (long[] value) -> { // Setter
                        try {
                            f.set(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (f.getType() == float.class) {
                    builder.addFloatProperty(key, () -> { // Getter
                        try {
                            return f.getFloat(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return Float.NaN;
                        }
                    }, (float value) -> { // Setter
                        try {
                            f.setFloat(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (f.getType() == float[].class) {
                    builder.addFloatArrayProperty(key, () -> { // Getter
                        try {
                            return (float[]) f.get(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }, (float[] value) -> { // Setter
                        try {
                            f.set(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (f.getType() == double.class) {
                    builder.addDoubleProperty(key, () -> { // Getter
                        try {
                            return f.getDouble(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return Double.NaN;
                        }
                    }, (double value) -> { // Setter
                        try {
                            f.setDouble(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (f.getType() == double[].class) {
                    builder.addDoubleArrayProperty(key, () -> { // Getter
                        try {
                            return (double[]) f.get(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }, (double[] value) -> { // Setter
                        try {
                            f.set(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (f.getType() == String.class) {
                    builder.addStringProperty(key, () -> { // Getter
                        try {
                            return (String) f.get(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }, (String value) -> { // Setter
                        try {
                            f.set(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

                } else if (f.getType() == String[].class) {
                    builder.addStringArrayProperty(key, () -> { // Getter
                        try {
                            return (String[]) f.get(obj);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }, (String[] value) -> { // Setter
                        try {
                            f.set(obj, value);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
                }

            
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void initSendable(SendableBuilder builder) {
            try {

                builder.setSmartDashboardType("tunableVal");
               
                for (TuneableField tf : val) {
                    addtoBuilder(builder, tf);
                   
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
