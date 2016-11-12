package com.example.android.sunshine.app;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

public class MyService extends WearableListenerService {
    public MyService() {
    }

    Double high;
    Double low;
    int highInt;
    int lowInt;

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        super.onDataChanged(dataEventBuffer);

        for(DataEvent dataEvent:dataEventBuffer){
            if(dataEvent.getType()==DataEvent.TYPE_CHANGED){
                DataMap dataMap = DataMapItem.fromDataItem(dataEvent.getDataItem()).getDataMap();
                String path = dataEvent.getDataItem().getUri().getPath();
                if (path.equals("/message")){
                    high = dataMap.getDouble("high");
                    low = dataMap.getDouble("low");

                    highInt = (int) Math.round(high);
                    lowInt = (int) Math.round(low);

                    System.out.println("Message wear is: "+high);
                }

            }
        }
        // Broadcast message to wearable activity for display
        Intent messageIntent = new Intent();
        messageIntent.setAction(Intent.ACTION_SEND);
        messageIntent.putExtra("high", String.valueOf(highInt));
        messageIntent.putExtra("low", String.valueOf(lowInt));

        LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
        System.out.println("Message wear is: "+high);


    }
}
