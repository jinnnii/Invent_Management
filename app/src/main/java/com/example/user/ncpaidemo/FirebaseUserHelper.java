package com.example.user.ncpaidemo;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.ncpaidemo.LoginActivity.strNick;

public class FirebaseUserHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefrenceUsers;
    private ArrayList<UserItem> bases; // 조회 결과 Array List
    List<String> keys; // 조회 결과 key List

    public interface DataStatus{
        void DataIsLoaded(ArrayList<UserItem> userItems, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseUserHelper(){
        mDatabase = FirebaseDatabase.getInstance();
        mRefrenceUsers = mDatabase.getReference("UserInfo").child(strNick).child("식자재목록");

    }


    public void readUserItem(FirebaseUserHelper.DataStatus dataStatus){

        mRefrenceUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    bases = new ArrayList<>();
                    bases.clear();

                    keys = new ArrayList<>();
                    for (DataSnapshot keyNode : snapshot.getChildren()) {
                        keys.add(keyNode.getKey());
                        UserItem userItem = keyNode.getValue(UserItem.class);
                        bases.add(userItem);
                    }

                        dataStatus.DataIsLoaded(bases,keys);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        public void readsCategory(FirebaseUserHelper.DataStatus dataStatus){
            mRefrenceUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    bases = new ArrayList<>();
                    bases.clear();

                    keys = new ArrayList<>();
                    for (DataSnapshot keyNode : snapshot.getChildren()) {
                        keys.add(keyNode.getKey());
                        UserItem userItem = keyNode.getValue(UserItem.class);
                        bases.add(userItem);
                    }

                    dataStatus.DataIsLoaded(bases,keys);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        public void addUserItem (ArrayList<UserItem> userItems, final DataStatus dataStatus){

            for(int i=0; i<userItems.size(); i++) {
                String key = mRefrenceUsers.push().getKey(); //note 키 생성
                mRefrenceUsers.child(key).setValue(userItems.get(i)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });
            }
        }

        public void updateUserItem(String key, UserItem userItem, final DataStatus dataStatus){
            mRefrenceUsers.child(strNick).child("식자재목록").child(key).setValue(userItem)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated();
                    }
            });
        }
        public void deleteUserItem(String key,final DataStatus dataStatus){
            mRefrenceUsers.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsDeleted();
                    }
                });
        }

        public void addandUpate(ArrayList<UserItem> list, FirebaseUserHelper.DataStatus dataStatus){

            ArrayList<UserItem> addItem = new ArrayList<>(); //새로 추가할 아이템
            ArrayList<UserItem> updateItem = new ArrayList<>(); // 업데이트할 아이템
            ArrayList<Integer> position = new ArrayList<>();

            readUserItem(dataStatus);

            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < bases.size(); j++) {
                    if (list.get(i).equals(bases.get(j))) {
                        updateItem.add(list.get(i));
                        position.add(j);

                        break;
                    } else {
                        addItem.add(list.get(i));
                    }
                }
            }
            addUserItem (addItem, dataStatus);

            for(int i=0; i<updateItem.size(); i++) {
                //updateItem.setAll(datas.get(position.get(i)))
                //updateItem(keys.get(position.get(i)),updateItem.get(i),dataStatus);
            }

        }

    }
