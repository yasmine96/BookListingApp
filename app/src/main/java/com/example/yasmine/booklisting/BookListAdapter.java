package com.example.yasmine.booklisting;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookListAdapter extends ArrayAdapter<BookList> {


    private static final String LOG_TAG = BookListAdapter.class.getSimpleName();

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context   The current context. Used to inflate the layout file.
     * @param books A List of AndroidFlavor objects to display in a list
     */
    public BookListAdapter(Activity context, List<BookList> books) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, books);
    }


    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link booklist} object located at this position in the list
        BookList currentList = getItem(position);



        // Find the TextView in the list_item.xml layout with the ID  title name
        TextView titleNameView = (TextView) listItemView.findViewById(R.id.title);

        String titleName = currentList.getTitle();
        // Display the title name of the current book in that TextView
       titleNameView.setText(titleName);


        // Find the TextView in the list_item.xml layout with the ID  author name
       // TextView authorNameView = (TextView) listItemView.findViewById(R.id.author);

       // String authorName = currentList.getAuthor();
        // Display the author name of the current book in that TextView
        //authorNameView.setText(authorName);


        // Find the TextView with view ID date
        TextView publishingYearView = (TextView) listItemView.findViewById(R.id.published);

        String publishingYear = currentList.getPublishingYear();
        // Display the author name of the current book in that TextView
        publishingYearView.setText(publishingYear);



        return listItemView;
    }


}

