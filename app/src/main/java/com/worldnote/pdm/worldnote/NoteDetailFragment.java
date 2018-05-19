package com.worldnote.pdm.worldnote;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.worldnote.pdm.worldnote.model.Note;
import com.worldnote.pdm.worldnote.provider.NoteViewModel;

/**
 * A fragment representing a single Note detail screen.
 * This fragment is either contained in a {@link NoteListActivity}
 * in two-pane mode (on tablets) or a {@link NoteDetailActivity}
 * on handsets.
 */
public class NoteDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Note mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NoteDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            final String noteId = getArguments().getString(ARG_ITEM_ID);
            new NoteViewModel().getNote(noteId).observe(this, new Observer<Note>() {
                @Override
                public void onChanged(@Nullable Note note) {
                    if (note != null) {
                        mItem = note;
                        Activity activity = NoteDetailFragment.this.getActivity();
                        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
                        if (appBarLayout != null) {
                            appBarLayout.setTitle(mItem.getTitle());
                        }
                    } else {
                        Log.d("NoteDetail", "Nota " + noteId + " não encontrada!");
                        NoteDetailFragment.this.finish();
                    }
                }
            });
        }
    }

    private void finish() {
        finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.note_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.note_detail)).setText(mItem.getContent());
        }

        return rootView;
    }
}
