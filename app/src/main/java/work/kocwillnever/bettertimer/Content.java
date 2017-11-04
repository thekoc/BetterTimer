package work.kocwillnever.bettertimer;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static work.kocwillnever.bettertimer.BetterTimerContract.*;


/**
 * Created by koc on 31/10/2017.
 */

final class BetterTimerContract {
    private BetterTimerContract() {
    }

    /* Inner class that defines the table contents */
    public static class PlanEntry implements BaseColumns {
        public static final String TABLE_NAME = "plan";
        public static final String COLUMN_NAME_PLAN_ID = "plan_id";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_PLAN_NAME = "plan_name";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_STATUS = "status";
    }

    public static class PoolEntry implements BaseColumns {
        public static final String TABLE_NAME = "pool";
        public static final String COLUMN_NAME_GOODS_ID = "goods_id";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_ITEM_NUM = "item_num";
    }


    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PlanEntry.TABLE_NAME + " (" +
                    PlanEntry.COLUMN_NAME_PLAN_ID + INT_TYPE + " PRIMARY KEY," +
                    PlanEntry.COLUMN_NAME_PLAN_NAME + TEXT_TYPE + COMMA_SEP +
                    PlanEntry.COLUMN_NAME_USER_ID + INT_TYPE + COMMA_SEP +
                    PlanEntry.COLUMN_NAME_DURATION + INT_TYPE + COMMA_SEP +
                    PlanEntry.COLUMN_NAME_STATUS + INT_TYPE + " );" +
            "CREATE TABLE "  + PoolEntry.TABLE_NAME + " (" +
                    PoolEntry.COLUMN_NAME_GOODS_ID + INT_TYPE + " PRIMARY KEY," +
                    PoolEntry.COLUMN_NAME_USER_ID + INT_TYPE + " PRIMARY KEY," +
                    PoolEntry.COLUMN_NAME_ITEM_NUM + INT_TYPE + COMMA_SEP + " );";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PlanEntry.TABLE_NAME + ";" +
            "DROP TABLE IF EXISTS" + PoolEntry.TABLE_NAME + ";";

    public static class BetterTimerDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "BetterTimer.db";

        public BetterTimerDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}



public class Content {
    // TODO: Return true data
    Resources resources;
    private Context context;
    private BetterTimerDbHelper dbHelper;
    private SQLiteDatabase db;
    public Content createContent(Context context) {
        return new Content(context);
    }

    public Content(Context context) {
        this.context = context;
        this.resources = context.getResources();
        dbHelper = new BetterTimerDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public class User {
        private int id;
        private int coinNum;
        private String telNum;
        private String name;
        private String motto;
        private Bitmap portrait;

        public int getCoinNum() {
            return coinNum;
        }

        public int getId() {
            return id;
        }

        public String getTelNum() {
            return telNum;
        }

        public String getName() {
            return name;
        }

        public String getMotto() {
            return motto;
        }

        public Bitmap getPortrait() {
            return portrait;
        }

        private User(int id, int coinNum, String telNum, String name, String motto, Bitmap portrait) {
            this(id, coinNum, telNum, name, motto);
            this.portrait = portrait;
        }

        private User(int id, int coinNum, String telNum, String name, String motto) {
            this.id = id;
            this.coinNum = coinNum;
            this.telNum = telNum;
            this.name = name;
            this.motto = motto;
        }
    }
    public class Plan {
        private int id;
        private int userId;
        private String name;
        private int duration; // as second
        private boolean completed;



        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getDuration() {
            return duration;
        }

        public boolean isCompleted() {
            return completed;
        }

        public User getOwner() {
            return new UserInfoHelper().getCurrentUser();
        }

        public Plan(int id, int userId, String name, int duration, boolean completed) {
            this.id = id;
            this.userId = userId;
            this.name = name;
            this.duration = duration;
            this.completed = completed;
        }
    }
    public class StudyRecord {
        private int id;
        private int userId;
        private int gainedCoinNum;
        private int duration; // as second
        private int planId;
        private boolean completed;

        public int getId() {
            return id;
        }

        public int getGainedCoinNum() {
            return gainedCoinNum;
        }

        public int getDuration() {
            return duration;
        }

        public boolean isCompleted() {
            return completed;
        }

        public User getOwner() {
            return new UserInfoHelper().getCurrentUser();
        }

        public int getPlanId() {
            return planId;
        }

        public Plan getPlan() {
            return new PlanHelper().getPlan(getPlanId());
        }

        StudyRecord(int id, int userId, int gainedCoinNum, int duration, int planId, boolean completed) {
            this.id = id;
            this.userId = userId;
            this.gainedCoinNum = gainedCoinNum;
            this.duration = duration;
            this.planId = planId;
            this.completed = completed;
        }
    }
    public class Goods {
        private int id;
        private String name;
        private int price;
        private String notes;
        private Bitmap Image;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public String getNotes() {
            return notes;
        }

        public Bitmap getImage() {
            return Image;
        }

        Goods(int id, String name, int price, String notes, Bitmap image) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.notes = notes;
            Image = image;
        }

    }
    public class Order {
        private int id;
        private int userId;
        private int goodsId;
        private int payedCoinNum;

        public int getId() {
            return id;
        }

        public int getPayedCoinNum() {
            return payedCoinNum;
        }

        public User getBuyer() {
            return new UserInfoHelper().getCurrentUser();
        }

        public int getGoodsId() {
            return goodsId;
        }

        public Goods getGoodsInfo() {
            return new GoodsHelper().getGoods(getGoodsId());
        }

        public Order(int id, int userId, int goodsId, int payedCoinNum) {
            this.id = id;
            this.userId = userId;
            this.goodsId = goodsId;
            this.payedCoinNum = payedCoinNum;
        }
    }
    public class PoolItem {
        private int itemNum;
        private int goodsId;
        private int userId;

        public int getItemNum() {
            return itemNum;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public int getUserId() {
            return userId;
        }

        public Goods getGoodsInfo() {
            return new GoodsHelper().getGoods(getGoodsId());
        }

        PoolItem(int goodsId, int userId, int itemNum) {
            this.itemNum = itemNum;
            this.goodsId = goodsId;
            this.userId = userId;
        }
    }

    private class UserInfoHelper {
        private User getCurrentUser() {
            return new User(1, 100, "110", "青蛙王子", "努力学习洋文");
        }
    }
    private class PlanHelper {
        PlanHelper() {
            clearPlans();
            class fakeDataGenerator {
                private void generate() {
                    addPlan("学洋文");
                    addPlan("延年益寿");
                }
            }
            new fakeDataGenerator().generate();
        }
        class PlanDbHelper {
            private List<Plan> getPlans(String selection, String[] selectionArgs) {
                final String[] projection = {
                        PlanEntry.COLUMN_NAME_PLAN_ID,
                        PlanEntry.COLUMN_NAME_USER_ID,
                        PlanEntry.COLUMN_NAME_PLAN_NAME,
                        PlanEntry.COLUMN_NAME_DURATION,
                        PlanEntry.COLUMN_NAME_STATUS
                };

                List<Plan> plans = new ArrayList<>();
                Cursor c = db.query(
                        PlanEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );


                if (c != null && c.moveToFirst()) {
                    do {
                        plans.add(getCursorPlan(c));
                    } while (c.moveToNext());
                }
                return plans;
            }

            private Plan getCursorPlan(Cursor cursor) {
                int planId = cursor.getInt(
                        cursor.getColumnIndexOrThrow(PlanEntry.COLUMN_NAME_PLAN_ID)
                );
                int userId = cursor.getInt(
                        cursor.getColumnIndexOrThrow(PlanEntry.COLUMN_NAME_USER_ID)
                );
                String planName = cursor.getString(
                        cursor.getColumnIndexOrThrow(PlanEntry.COLUMN_NAME_PLAN_NAME)
                );
                int duration = cursor.getInt(
                        cursor.getColumnIndexOrThrow(PlanEntry.COLUMN_NAME_DURATION)
                );
                int status = cursor.getInt(
                        cursor.getColumnIndexOrThrow(PlanEntry.COLUMN_NAME_STATUS)
                );
                boolean completed  = (status == 1);
                return new Plan(planId, userId, planName, duration, completed);
            }
        }
        private Plan getPlan(int id) {
            return new PlanDbHelper().getPlans("ID = ?", new String[]{String.valueOf(id)}).get(0);
        }
        private List<Plan> getAllPlans() {
            return new PlanDbHelper().getPlans(null, null);
        }
    }
    private class StudyRecordHelper {
        public StudyRecord getStudyRecord(int id) {
            return getAllStudyRecords().get(id);
        }

        public List<StudyRecord> getAllStudyRecords() {
            class FakeDataGenerator {
                private List<StudyRecord> generate() {
                    List<StudyRecord> records = new ArrayList<>();
                    records.add(new StudyRecord(1, getUserID(), 10, 3600, 1, true));
                    records.add(new StudyRecord(2, getUserID(), 10, 3600, 2, true));
                    return records;
                }
            }

            return new FakeDataGenerator().generate();
        }
    }
    private class GoodsHelper {
        private Goods getGoods(int id) {
            return getAllGoods().get(id);
        }

        private List<Goods> getAllGoods() {
            class FakeDataGenerator {
                private List<Goods> generate() {
                    List<Goods> goods = new ArrayList<>();
                    goods.add(new Goods(1, "黑框眼镜", 100, "", BitmapFactory.decodeResource(resources, R.drawable.black)));
                    goods.add(new Goods(2, "高腰裤", 1000, "", BitmapFactory.decodeResource(resources, R.drawable.pants)));
                    return goods;
                }
            }
            return new FakeDataGenerator().generate();
        }
    }
    private class OrderHelper {
        private Order getOrder(int id) {
            return getAllOrder().get(id);
        }

        private List<Order> getAllOrder() {
            class FakeDataGenerator {
                private List<Order> generate() {
                    List<Order> orders = new ArrayList<>();
                    orders.add(new Order(1, getUserID(), 1, 100));
                    orders.add(new Order(2, getUserID(), 2, 200));
                    return orders;
                }
            }
            return new FakeDataGenerator().generate();
        }
    }
    private class PoolItemHelper {
        private PoolItem getPoolItem(int id) {
            return getAllPoolItems().get(id);
        }

        private List<PoolItem> getAllPoolItems() {
            class FakeDataGenerator {
                private List<PoolItem> generate() {
                    List<PoolItem> items = new ArrayList<>();
                    items.add(new PoolItem(1, getUserID(), 10));
                    return items;
                }
            }
            return new FakeDataGenerator().generate();
        }
    }

    public int getUserID() {
        return new UserInfoHelper().getCurrentUser().getId();
    }

    public User getCurrentUser() {
        return new UserInfoHelper().getCurrentUser();
    }

    public List<Plan> getPlans() {
        return new PlanHelper().getAllPlans();
    }

    public void addPlan(String name) {
        class PlanDbHelper {
            public void insertPlan(String name) {
                ContentValues values = new ContentValues();
                values.put(PlanEntry.COLUMN_NAME_USER_ID, getUserID());
                values.put(PlanEntry.COLUMN_NAME_PLAN_NAME, name);
                values.put(PlanEntry.COLUMN_NAME_DURATION, 0);
                values.put(PlanEntry.COLUMN_NAME_STATUS, 0);
                db.insert(PlanEntry.TABLE_NAME, null, values);
            }
        }

        new PlanDbHelper().insertPlan(name);
    }

    public void clearPlans() {
        String selection = null;
        String[] selectionArgs = null;
        db.delete(PlanEntry.TABLE_NAME, selection, selectionArgs);
    }

    public void completePlan(int id) {
        ContentValues values = new ContentValues();
        String selection = PlanEntry.COLUMN_NAME_PLAN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        values.put(PlanEntry.COLUMN_NAME_STATUS, 1);
        db.update(PlanEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    public void addRecord(int gainedCoinNum, int duration, int planId, boolean completed) {

    }

    public List<Order> getOrders() {
        return new OrderHelper().getAllOrder();
    }

    public List<StudyRecord> getStudyRecords() {
        return new StudyRecordHelper().getAllStudyRecords();
    }

    public List<Goods> getAllGoods() {
        return new GoodsHelper().getAllGoods();
    }

    public List<PoolItem> getPoolItems() {
        return new PoolItemHelper().getAllPoolItems();
    }
}
