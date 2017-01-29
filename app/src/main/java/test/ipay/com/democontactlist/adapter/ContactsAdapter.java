package test.ipay.com.democontactlist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import test.ipay.com.democontactlist.R;
import test.ipay.com.democontactlist.model.SelectUser;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView contactImage;
        public TextView contactName, contactPhone, contactNoImage;


        public ViewHolder(View itemView) {
            super(itemView);
            contactName = (TextView) itemView.findViewById(R.id.contact_name);
            contactPhone = (TextView) itemView.findViewById(R.id.contact_phone);
            contactImage = (CircleImageView) itemView.findViewById(R.id.contact_image);
            contactNoImage = (TextView) itemView.findViewById(R.id.contact_list_user_without_image);
        }
    }


    private ArrayList<SelectUser> _data;

    private Context mContext;

    public ContactsAdapter(Context context, ArrayList<SelectUser> _data) {
        this._data = _data;
        this.mContext = context;

        SelectUser inviteContact = new SelectUser();
        inviteContact.setName("Load More");
        inviteContact.setPhone("");
        _data.add(inviteContact);
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        View contactView = inflater.inflate(R.layout.contact_info, parent, false);


        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final SelectUser data = _data.get(position);

        if (position == getLastItemCount()) {
            TextView contactName = viewHolder.contactName;
            contactName.setText(data.getName());
            contactName.setTextSize(18);
            TextView contactPhone = viewHolder.contactPhone;
            contactPhone.setVisibility(View.GONE);
            CircleImageView contactImage = viewHolder.contactImage;
            contactImage.setVisibility(View.GONE);
            TextView contactNoImage = viewHolder.contactNoImage;
            contactNoImage.setVisibility(View.GONE);
        }

        TextView contactName = viewHolder.contactName;
        contactName.setText(data.getName());
        TextView contactPhone = viewHolder.contactPhone;
        contactPhone.setText(data.getPhone());
        CircleImageView contactImage = viewHolder.contactImage;
        contactImage.setImageResource(R.drawable.dummy_user_image);
        contactImage.setVisibility(View.GONE);
        TextView contactNoImage = viewHolder.contactNoImage;
        contactNoImage.setText(String.valueOf(data.getName().toString().charAt(0)));

}

    @Override
    public int getItemCount() {
        return _data.size();
    }

    public int getLastItemCount() {
        return _data.size() - 1;
    }

}