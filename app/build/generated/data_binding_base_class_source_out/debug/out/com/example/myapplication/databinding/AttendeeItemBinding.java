// Generated by view binder compiler. Do not edit!
package com.example.myapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.myapplication.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class AttendeeItemBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView attendeeResponse;

  @NonNull
  public final TextView externalAttendeeName;

  @NonNull
  public final TextView externalAttendeeNumber;

  @NonNull
  public final ImageView imageView;

  private AttendeeItemBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView attendeeResponse, @NonNull TextView externalAttendeeName,
      @NonNull TextView externalAttendeeNumber, @NonNull ImageView imageView) {
    this.rootView = rootView;
    this.attendeeResponse = attendeeResponse;
    this.externalAttendeeName = externalAttendeeName;
    this.externalAttendeeNumber = externalAttendeeNumber;
    this.imageView = imageView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AttendeeItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AttendeeItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.attendee_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AttendeeItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.attendeeResponse;
      TextView attendeeResponse = ViewBindings.findChildViewById(rootView, id);
      if (attendeeResponse == null) {
        break missingId;
      }

      id = R.id.externalAttendeeName;
      TextView externalAttendeeName = ViewBindings.findChildViewById(rootView, id);
      if (externalAttendeeName == null) {
        break missingId;
      }

      id = R.id.externalAttendeeNumber;
      TextView externalAttendeeNumber = ViewBindings.findChildViewById(rootView, id);
      if (externalAttendeeNumber == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      return new AttendeeItemBinding((ConstraintLayout) rootView, attendeeResponse,
          externalAttendeeName, externalAttendeeNumber, imageView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
