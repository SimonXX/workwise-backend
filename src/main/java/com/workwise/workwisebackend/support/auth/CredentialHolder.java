package com.workwise.workwisebackend.support.auth;

import com.workwise.workwisebackend.entities.Credential;
import com.workwise.workwisebackend.entities.Role;

public interface CredentialHolder {
    Credential getCredentials();
    Role getRole();
}
