package brokerscircle.com.api_helpers;

import java.util.ArrayList;
import java.util.List;

import brokerscircle.com.model.ChatUtils;

public class ChatDatabaseHelper {

    private List<ChatUtils> mChatList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<ChatUtils> chatUtils, List<String> keys);
    }

    public ChatDatabaseHelper() {
    }

    public void readChatList( final DataStatus dataStatus){

        mChatList.clear();
        List<String> keys = new ArrayList<>();

        //Looping Start

        //Keys for update recyclerview
        keys.add("1");
        ChatUtils chatUtils = new ChatUtils("1","https://ik.imagekit.io/demo/img/tr:f-jpg/medium_cafe_B1iTdD0C.jpg", "Greg Jonson","Hey there i am using Brokers Hub!", "Today", true);
        mChatList.add(chatUtils);

        keys.add("2");
        chatUtils = new ChatUtils("2","https://ik.imagekit.io/demo/img/tr:f-jpg/medium_cafe_B1iTdD0C.jpg", "Amaan Ahmed","Hello to the Brokers Hub.", "Yesterday", false);
        mChatList.add(chatUtils);

        keys.add("3");
        chatUtils = new ChatUtils("3","https://ik.imagekit.io/demo/img/tr:f-jpg/medium_cafe_B1iTdD0C.jpg", "Alexender","WOW, that's awesome!", "2 Days ago", true);
        mChatList.add(chatUtils);

        keys.add("4");
        chatUtils = new ChatUtils("4","https://ik.imagekit.io/demo/img/tr:f-jpg/medium_cafe_B1iTdD0C.jpg", "Alex Summy","Hey there i am using Brokers Hub!", "1 Week ago", false);
        mChatList.add(chatUtils);

        keys.add("5");
        chatUtils = new ChatUtils("5","https://ik.imagekit.io/demo/img/tr:f-jpg/medium_cafe_B1iTdD0C.jpg", "Mohammad Aslam","WOW, that's awesome!", "1 Month ago", true);
        mChatList.add(chatUtils);

        keys.add("6");
        chatUtils = new ChatUtils("6","https://ik.imagekit.io/demo/img/tr:f-jpg/medium_cafe_B1iTdD0C.jpg", "Amjad Ansari","Hey there i am using Brokers Hub!", "2 Months ago", false);
        mChatList.add(chatUtils);

        keys.add("7");
        chatUtils = new ChatUtils("7","https://ik.imagekit.io/demo/img/tr:f-jpg/medium_cafe_B1iTdD0C.jpg", "Ahmed Raza","WOW, that's awesome!", "1 Year ago", true);
        mChatList.add(chatUtils);

        keys.add("8");
        chatUtils = new ChatUtils("8","https://ik.imagekit.io/demo/img/tr:f-jpg/medium_cafe_B1iTdD0C.jpg", "Ibtesam Umar","Hey there i am using Brokers Hub!", "2 Years ago", false);
        mChatList.add(chatUtils);

        keys.add("9");
        chatUtils = new ChatUtils("9","https://ik.imagekit.io/demo/img/tr:f-jpg/medium_cafe_B1iTdD0C.jpg", "Luqmaan Hussain","WOW, that's awesome!", "5 Years ago", true);
        mChatList.add(chatUtils);
        //End Looping Start

        dataStatus.DataIsLoaded(mChatList,keys);
    }

}
