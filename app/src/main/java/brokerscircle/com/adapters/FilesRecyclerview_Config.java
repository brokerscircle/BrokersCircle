package brokerscircle.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import brokerscircle.com.model.Files.FilesData;
import brokerscircle.com.R;

public class FilesRecyclerview_Config {

    private Context mContext;
    private FileAdapter mFileAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<FilesData> filesData){
        mContext = context;
        mFileAdapter = new FileAdapter(filesData);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
        recyclerView.setAdapter(mFileAdapter);
    }

    class VideoItemView extends RecyclerView.ViewHolder{

        private ImageView mImage;

        public VideoItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.single_image_layout, parent, false));

            mImage =  itemView.findViewById(R.id.image);
        }

        public void bind(final FilesData filesData){

            //Profile image
            if (filesData.getThumbnail() == null){
                mImage.setImageResource(R.mipmap.mainscreenbackground);
            }else{
                Picasso.get().load(filesData.getThumbnail().toString()).placeholder(R.mipmap.mainscreenbackground).centerCrop().fit().into(mImage);
            }
        }
    }

    class FileAdapter extends RecyclerView.Adapter<VideoItemView>{

        private List<FilesData> mFileList;

        public FileAdapter(List<FilesData> mFileList) {
            this.mFileList = mFileList;
        }

        @NonNull
        @Override
        public VideoItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VideoItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull VideoItemView holder, int position) {
            holder.bind(mFileList.get(position));
        }

        @Override
        public int getItemCount() {
            return mFileList.size();
        }
    }

}
