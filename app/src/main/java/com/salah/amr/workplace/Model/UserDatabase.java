package com.salah.amr.workplace.Model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.*;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 12/5/2017.
 */

public class UserDatabase {

    private double mPhotoUploadProgress = 0;

    public interface ICallbackGetEmployees {
        void callbackGetEmployees(List<User> users);
    }

    public interface IChatRoomLoginFormCallback {
        void loginCallback(boolean flag);
    }

    public interface IChatRoomSetupCallback {
        void callbackRegisterChatRoom(boolean flag);
    }

    public interface ICallbackGetUserById {
        void callbackGetUserById(User user);
    }

    public interface ICallbackUploadImage {
        void callbackUploadImage(String imageURL);
    }

    public interface ICallbackGetMessages {
        void callbackGetMessages(List<Message> messages, List<User> users);
    }

    public interface ICallbackSendMessage {
        void callbackSendMessage(boolean flag);
    }

    public interface ICallbackCheckDeleted {
        void callbackCheckDeleted(Boolean flag);
    }

    public interface ICallbackAuthIsNull {
        void callbackAuthIsNull();
    }


    private static final String TAG = "UserDatabase";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    FirebaseAuth.AuthStateListener authListener;
    FirebaseAuth auth;
    FirebaseUser chatRoom;
    StorageReference storage;



    public UserDatabase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        auth = FirebaseAuth.getInstance();
        Log.d(TAG, "UserDatabase: auth " + auth.getCurrentUser());
        chatRoom = auth.getCurrentUser();
        storage = FirebaseStorage.getInstance().getReference();

    }

    public void registerChatRoom(String email, String password, IChatRoomSetupCallback IChatRoomSetupCallback) {
        Log.d(TAG, "callbackRegisterChatRoom: ");
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            Log.d(TAG, "callbackRegisterChatRoom: completed");
            if (task.isSuccessful()) {
                IChatRoomSetupCallback.callbackRegisterChatRoom(true);
            } else {
                IChatRoomSetupCallback.callbackRegisterChatRoom(false);
            }

        });
    }

    public void insertUsers(List<User> userList, String chatRoomName) {
        Log.d(TAG, "insertUsers: ");
        chatRoom = auth.getCurrentUser();
        User user = new User(-1, "Manager", "", "", "");
        myRef.child(chatRoomName).child("Manager").setValue(user);
        for (int i = 0; i < userList.size(); i++) {
            Log.d(TAG, "insertUsers: " + userList.get(i).getName());
            myRef.child(chatRoomName).child("Employees").child(chatRoom.getUid() + userList.get(i).getId()).setValue(userList.get(i));
        }
    }

    public void getEmployees(String chatRoomName, ICallbackGetEmployees callback) {
        Log.d(TAG, "getEmployees: ");
        List<User> users = new ArrayList<>();
        myRef.child(chatRoomName).child("Employees").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded: " + dataSnapshot.getValue(User.class));
                User user = dataSnapshot.getValue(User.class);
                if (!user.getDeleted())
                    users.add(user);
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: ");
                callback.callbackGetEmployees(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void chatRoomLogin(String email, String password, IChatRoomLoginFormCallback callback) {
        Log.d(TAG, "chatRoomLogin: ");
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.loginCallback(true);
            } else {
                callback.loginCallback(false);
            }
        });
    }

    public void getUserById(String chatRoomName, int id, ICallbackGetUserById callback) {
        Log.d(TAG, "getUserById: " + chatRoomName + id);
        if (auth.getCurrentUser() == null) {
            callback.callbackGetUserById(null);
        }
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (id == -1) {
                    chatRoom = auth.getCurrentUser();
                    User user = dataSnapshot.child(chatRoomName).child("Manager").getValue(User.class);
                    if (callback != null) {
                        callback.callbackGetUserById(user);
                    }
                } else {
                    chatRoom = auth.getCurrentUser();
                    User user = dataSnapshot.child(chatRoomName).child("Employees").child(chatRoom.getUid() + id).getValue(User.class);
                    if (callback != null)
                        callback.callbackGetUserById(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void uploadImage(byte[] bytes, String chatRoomName, String id, ICallbackUploadImage callback) {
        Log.d(TAG, "uploadImage: " + chatRoomName + id);
        StorageReference storageReference = storage.child("photos").child(chatRoomName)
                .child(chatRoom.getUid() + id);

        UploadTask uploadTask = null;
        uploadTask = storageReference.putBytes(bytes);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            Log.d(TAG, "uploadImage: success");
            Uri firebaseUrl = taskSnapshot.getDownloadUrl();
            callback.callbackUploadImage(firebaseUrl.toString());

        }).addOnFailureListener(e -> {
            Log.d(TAG, "onFailure: Photo upload failed.");
            callback.callbackUploadImage(null);
        }).addOnProgressListener(taskSnapshot -> {
            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

            if (progress - 15 > mPhotoUploadProgress) {
                mPhotoUploadProgress = progress;
            }

            Log.d(TAG, "onProgress: upload progress: " + progress + "% done");
        });
    }

    public void updateUser(String chatRoomName, int id, User user) {
        Log.d(TAG, "updateUser: ");
        chatRoom = auth.getCurrentUser();
        if (id == -1) {
            myRef.child(chatRoomName).child("Manager").setValue(user);
        } else {
            myRef.child(chatRoomName).child("Employees").child(chatRoom.getUid() + id).setValue(user);
        }
    }

    public void sendMessage(Message message, String chatRoomName, int flag, ICallbackSendMessage callback) {
        if (flag == 0) {
            myRef.child(chatRoomName).child("Messages").child("GroupChat").push().setValue(message).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    callback.callbackSendMessage(true);
                } else {
                    callback.callbackSendMessage(false);
                }
            });
        } else {
            myRef.child(chatRoomName).child("Messages").child("Announcements").push().setValue(message).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    callback.callbackSendMessage(true);
                } else {
                    callback.callbackSendMessage(false);
                }
            });
        }

    }

    public void getMessages(String chatRoomName, int flag, ICallbackGetMessages callback) {
        List<Message> messages = new ArrayList<>();
        List<User> users = new ArrayList<>();
        Log.d(TAG, "getMessages: "+chatRoomName);
        if (flag == 0) {
            myRef.child(chatRoomName).child("Messages").child("GroupChat").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Message message = dataSnapshot.getValue(Message.class);
                    users.clear();
                    messages.add(message);

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (message.getUserId() == -1) {
                                chatRoom = auth.getCurrentUser();
                                User user = dataSnapshot.child(chatRoomName).child("Manager").getValue(User.class);
                                if (user != null)
                                    users.add(user);
                            } else {
                                chatRoom = auth.getCurrentUser();
                                User user = dataSnapshot.child(chatRoomName).child("Employees").child(chatRoom.getUid() + message.getUserId()).getValue(User.class);
                                if (user != null)
                                    users.add(user);
                            }
                            if (messages.size() == users.size()) {
                                callback.callbackGetMessages(messages, users);
                                Log.d(TAG, "onDataChange: doing callback of users called size" + users.size());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } else {
            myRef.child(chatRoomName).child("Messages").child("Announcements").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Message message = dataSnapshot.getValue(Message.class);
                    users.clear();
                    messages.add(message);

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d(TAG, "onDataChange: message" + message);
                            if (message.getUserId() == -1) {
                                chatRoom = auth.getCurrentUser();
                                User user = dataSnapshot.child(chatRoomName).child("Manager").getValue(User.class);
                                Log.d(TAG, "onDataChange: user" + user);
                                if (user != null)
                                    users.add(user);
                            } else {
                                chatRoom = auth.getCurrentUser();
                                User user = dataSnapshot.child(chatRoomName).child("Employees").child(chatRoom.getUid() + message.getUserId()).getValue(User.class);
                                Log.d(TAG, "onDataChange: user" + user);
                                if (user != null)
                                    users.add(user);
                            }
                            if (messages.size() == users.size()) {
                                callback.callbackGetMessages(messages, users);
                                Log.d(TAG, "onDataChange: doing callback of users called size" + users.size());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }


    }

    public void updateUserValues(String chatRoomName, int id, String userName, String imageURL, String email, String phone, Boolean selected) {
        if (id == -1) {
            Log.d(TAG, "updateUserValues: manager update data");
            if (userName != null)
                myRef.child(chatRoomName).child("Manager").child("name").setValue(userName);
            if (imageURL != null)
                myRef.child(chatRoomName).child("Manager").child("imageURL").setValue(imageURL);
            if (email != null)
                myRef.child(chatRoomName).child("Manager").child("email").setValue(email);
            if (phone != null)
                myRef.child(chatRoomName).child("Manager").child("phone").setValue(phone);
            if (selected != null)
                myRef.child(chatRoomName).child("Manager").child("selected").setValue(selected);
        } else {
            Log.d(TAG, "updateUserValues: employee update data");
            chatRoom = auth.getCurrentUser();
            if (userName != null)
                myRef.child(chatRoomName).child("Employees").child(chatRoom.getUid() + id).child("name").setValue(userName);
            if (imageURL != null)
                myRef.child(chatRoomName).child("Employees").child(chatRoom.getUid() + id).child("imageURL").setValue(imageURL);
            if (email != null)
                myRef.child(chatRoomName).child("Employees").child(chatRoom.getUid() + id).child("email").setValue(email);
            if (phone != null)
                myRef.child(chatRoomName).child("Employees").child(chatRoom.getUid() + id).child("phone").setValue(phone);
            if (selected != null)
                myRef.child(chatRoomName).child("Employees").child(chatRoom.getUid() + id).child("selected").setValue(selected);
        }
    }

    public void insertEmployee(String chatRoomName, int id, User user) {
        Log.d(TAG, "insertEmployee: " + user);
        chatRoom = auth.getCurrentUser();
        myRef.child(chatRoomName).child("Employees").child(chatRoom.getUid() + id).setValue(user);
    }

    public void removeEmployee(String chatRoomName, int id) {
        Log.d(TAG, "removeEmployee: ");
        chatRoom = auth.getCurrentUser();
        Boolean flag = true;
        myRef.child(chatRoomName).child("Employees").child(chatRoom.getUid() + id).child("deleted").setValue(flag);
    }

    public void checkIfDeleted(String chatRoomName, int id, ICallbackCheckDeleted callback) {
        Log.d(TAG, "checkIfDeleted: ");
        chatRoom = auth.getCurrentUser();
        if (id == -1) {
            Boolean flag = false;
            callback.callbackCheckDeleted(flag);
        } else {
            myRef.child(chatRoomName).child("Employees").child(chatRoom.getUid() + id).child("deleted").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Boolean flag = dataSnapshot.getValue(Boolean.class);
                    callback.callbackCheckDeleted(flag);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    public void signOut(){

    }


}