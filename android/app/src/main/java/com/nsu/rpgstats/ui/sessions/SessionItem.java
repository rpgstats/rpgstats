package com.nsu.rpgstats.ui.sessions;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mikepenz.fastadapter.binding.AbstractBindingItem;
import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.SessionItemBinding;
import com.nsu.rpgstats.entities.Session;

import java.util.List;

public class SessionItem extends AbstractBindingItem<SessionItemBinding> {

    private Session session;

    public SessionItem(Session session) {
        this.session = session;
    }

    @Override
    public long getIdentifier() {
        return session.getId();
    }

    @NonNull
    @Override
    public SessionItemBinding createBinding(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup) {
        return SessionItemBinding.inflate(layoutInflater, viewGroup, false);
    }

    @Override
    public void bindView(@NonNull SessionItemBinding binding, @NonNull List<?> payloads) {
        binding.sessionNameTextView.setText(session.getName());
        binding.sessionCreationDateTextView.setText(session.getCreationDate());
    }

    @Override
    public void unbindView(@NonNull SessionItemBinding binding) {
        binding.sessionNameTextView.setText(null);
        binding.sessionCreationDateTextView.setText(null);
    }

    @Override
    public int getType() {
        return R.id.sessionsRecyclerView;
    }
}
