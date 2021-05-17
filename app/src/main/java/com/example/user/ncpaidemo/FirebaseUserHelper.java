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

import static com.example.user.ncpaidemo.SelectBaseActivity.lStr;

public class FirebaseUserHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefrenceUsers;
    private ArrayList<DatabaseReference> mRefList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<UserItem> userItems,List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseUserHelper(){
        mDatabase = FirebaseDatabase.getInstance();
        mRefrenceUsers = mDatabase.getReference("UserInfo");
        mRefList.add(mRefrenceUsers);

    }


    public void fireBase(FirebaseDatabaseHelper.DataStatus dataStatus){

        mRefrenceUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    List<UserItem> bases = new ArrayList<>();
                    bases.clear();

                    List<String> keys = new ArrayList<>();
                    for (DataSnapshot keyNode : snapshot.getChildren()) {
                        keys.add(keyNode.getKey());
                        UserItem userItem = keyNode.getValue(UserItem.class);
                        bases.add(userItem);
                    }

                    //dataStatus.DataIsLoaded(bases,keys);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        public void addUserItem (ArrayList<UserItem> userItems, final DataStatus dataStatus){
            String key = mRefrenceUsers.push().getKey();
            mRefrenceUsers.child("김은진").child("식자재목록").child(key).setValue(userItems).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    dataStatus.DataIsInserted();
                }
            });
        }
    }
