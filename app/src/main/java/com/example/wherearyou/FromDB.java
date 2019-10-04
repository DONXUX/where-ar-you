package com.example.wherearyou;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class FromDB {
    private FirebaseAuth mAuth;
    public static String friendName;
    public static Double friendLatitude;
    public static Double friendLongitude;
    public static String friendAddress;
    public static String sharingFriendName;
    FragFriends fragFriends = new FragFriends();

    public void friendLocation(){

        ToDB toDB = new ToDB();
        final DatabaseReference sharing = FirebaseDatabase.getInstance().getReference().child("User").child(toDB.EmailToId);

        sharing.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sharingFriendName = dataSnapshot.child("위치공유중").child("아이디").getValue(String.class);
                Log.d(TAG, "로그" + sharingFriendName);
                if(sharingFriendName != null){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference mRootRef = database.getInstance().getReference("User");
                    DatabaseReference nameRef = mRootRef.child(sharingFriendName);
                    DatabaseReference idRef = nameRef.child("이름");
                    DatabaseReference latitudeRef = nameRef.child("위도");
                    DatabaseReference longitudeRef = nameRef.child("경도");

                    DatabaseReference addressRef = nameRef.child("주소");
                    DatabaseReference timeRef = nameRef.child("시간");


                    idRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            friendName = dataSnapshot.getValue(String.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                    latitudeRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            friendLatitude = dataSnapshot.getValue(Double.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                    longitudeRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            friendLongitude = dataSnapshot.getValue(Double.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                    addressRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            friendAddress = dataSnapshot.getValue(String.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
