package brokerscircle.com.api_helpers;

import java.util.ArrayList;
import java.util.List;

import brokerscircle.com.model.MessageUtils;

public class MessageDatabaseHelper {

    private List<MessageUtils> mMessageList = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<MessageUtils> messageUtils, List<String> keys);
    }

    public MessageDatabaseHelper() {
    }

    public void readMessageList( final DataStatus dataStatus){

        mMessageList.clear();
        List<String> keys = new ArrayList<>();

        //Looping Start

        //Keys for update recyclerview
        keys.add("1");
        MessageUtils messageUtils = new MessageUtils("1","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since  the 1500s.", "11:30 PM","example@mail.com", true);
        mMessageList.add(messageUtils);

        keys.add("2");
        messageUtils = new MessageUtils("2","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since  the 1500s.", "12:05 AM","example@mail.com", false);
        mMessageList.add(messageUtils);

        keys.add("3");
        messageUtils = new MessageUtils("3","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since  the 1500s.", "6:00 AM","example@mail.com", false);
        mMessageList.add(messageUtils);

        keys.add("5");
        messageUtils = new MessageUtils("5","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since  the 1500s.", "10:20 AM","example@mail.com", true);
        mMessageList.add(messageUtils);

        keys.add("6");
        messageUtils = new MessageUtils("6","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since  the 1500s.", "12:05 AM","example@mail.com", false);
        mMessageList.add(messageUtils);

        keys.add("7");
        messageUtils = new MessageUtils("7","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since  the 1500s.", "6:00 AM","example@mail.com", false);
        mMessageList.add(messageUtils);

        keys.add("8");
        messageUtils = new MessageUtils("8","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since  the 1500s.", "10:20 AM","example@mail.com", true);
        mMessageList.add(messageUtils);

        //End Looping Start

        dataStatus.DataIsLoaded(mMessageList,keys);
    }

}
