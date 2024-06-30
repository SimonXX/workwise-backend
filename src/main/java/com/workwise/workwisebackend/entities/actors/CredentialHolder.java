package com.workwise.workwisebackend.entities.actors;

import com.workwise.workwisebackend.entities.Credential;
import com.workwise.workwisebackend.entities.Role;

public interface CredentialHolder {
    Credential getCredentials();
    Role getRole();
}
