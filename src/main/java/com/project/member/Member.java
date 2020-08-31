package com.project.member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

import static org.springframework.security.core.userdetails.User.builder;
import static org.springframework.security.core.userdetails.User.withUsername;

public class Member implements UserDetails {
    private final String username;
    private String password;
    private final String email;
    private final Set<GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public Member(String username, String password, String email,
                  Collection<? extends GrantedAuthority> authorities) {
        this(username, password, email, authorities,
                true, true, true, true);
    }

    public Member(String username, String password, String email,
                  Collection<? extends GrantedAuthority> authorities,
                  boolean accountNonExpired, boolean accountNonLocked,
                  boolean credentialsNonExpired, boolean enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void eraseCredentials() {
        password = null;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per
        // UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(
                new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority,
                    "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>,
            Serializable {
        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to
            // the set.
            // If the authority is null, it is a custom authority and should precede
            // others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("Username: ").append(this.username).append("; \n");
        sb.append("Password: [PROTECTED]; \n");
        sb.append("Email: ").append(this.email).append("; \n");
        sb.append("Enabled: ").append(this.enabled).append("; \n");
        sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; \n");
        sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; \n");
        sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; \n");

        if (!authorities.isEmpty()) {
            sb.append("Granted Authorities: ");

            boolean first = true;
            for (GrantedAuthority auth : authorities) {
                if (!first) {
                    sb.append(",");
                }
                first = false;

                sb.append(auth);
            }
        }
        else {
            sb.append("Not granted any authorities");
        }

        return sb.toString();
    }
//    public static UserBuilder withUsername(String username) {
//        return builder().username(username);
//    }
//
//    public static UserBuilder builder() {
//        return new UserBuilder();
//    }
//
//    @Deprecated
//    public static UserBuilder withDefaultPasswordEncoder() {
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        return builder().passwordEncoder(encoder::encode);
//    }
//
//    public static UserBuilder withUserDetails(UserDetails userDetails) {
//        return withUsername(userDetails.getUsername())
//                .password(userDetails.getPassword())
////                .email(userDetails.getEmail())
//                .accountExpired(!userDetails.isAccountNonExpired())
//                .accountLocked(!userDetails.isAccountNonLocked())
//                .authorities(userDetails.getAuthorities())
//                .credentialsExpired(!userDetails.isCredentialsNonExpired())
//                .disabled(!userDetails.isEnabled());
//    }
//
//    public static class UserBuilder {
//        private String username;
//        private String password;
//        private String email;
//        private List<GrantedAuthority> authorities;
//        private boolean accountExpired;
//        private boolean accountLocked;
//        private boolean credentialsExpired;
//        private boolean disabled;
//        private Function<String, String> passwordEncoder = password -> password;
//
//        /**
//         * Creates a new instance
//         */
//        private UserBuilder() {
//        }
//
//        /**
//         * Populates the username. This attribute is required.
//         *
//         * @param username the username. Cannot be null.
//         * @return the {@link User.UserBuilder} for method chaining (i.e. to populate
//         * additional attributes for this user)
//         */
//        public UserBuilder username(String username) {
//            Assert.notNull(username, "username cannot be null");
//            this.username = username;
//            return this;
//        }
//
//        /**
//         * Populates the password. This attribute is required.
//         *
//         * @param password the password. Cannot be null.
//         * @return the {@link User.UserBuilder} for method chaining (i.e. to populate
//         * additional attributes for this user)
//         */
//        public UserBuilder password(String password) {
//            Assert.notNull(password, "password cannot be null");
//            this.password = password;
//            return this;
//        }
//
//        public UserBuilder email(String email) {
//            Assert.notNull(email, "email cannot be null");
//            this.email = email;
//            return this;
//        }
//
//        /**
//         * Encodes the current password (if non-null) and any future passwords supplied
//         * to {@link #password(String)}.
//         *
//         * @param encoder the encoder to use
//         * @return the {@link User.UserBuilder} for method chaining (i.e. to populate
//         * additional attributes for this user)
//         */
//        public UserBuilder passwordEncoder(Function<String, String> encoder) {
//            Assert.notNull(encoder, "encoder cannot be null");
//            this.passwordEncoder = encoder;
//            return this;
//        }
//
//        /**
//         * Populates the roles. This method is a shortcut for calling
//         * {@link #authorities(String...)}, but automatically prefixes each entry with
//         * "ROLE_". This means the following:
//         *
//         * <code>
//         *     builder.roles("USER","ADMIN");
//         * </code>
//         *
//         * is equivalent to
//         *
//         * <code>
//         *     builder.authorities("ROLE_USER","ROLE_ADMIN");
//         * </code>
//         *
//         * <p>
//         * This attribute is required, but can also be populated with
//         * {@link #authorities(String...)}.
//         * </p>
//         *
//         * @param roles the roles for this user (i.e. USER, ADMIN, etc). Cannot be null,
//         * contain null values or start with "ROLE_"
//         * @return the {@link User.UserBuilder} for method chaining (i.e. to populate
//         * additional attributes for this user)
//         */
//        public UserBuilder roles(String... roles) {
//            List<GrantedAuthority> authorities = new ArrayList<>(
//                    roles.length);
//            for (String role : roles) {
//                Assert.isTrue(!role.startsWith("ROLE_"), () -> role
//                        + " cannot start with ROLE_ (it is automatically added)");
//                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
//            }
//            return authorities(authorities);
//        }
//
//        /**
//         * Populates the authorities. This attribute is required.
//         *
//         * @param authorities the authorities for this user. Cannot be null, or contain
//         * null values
//         * @return the {@link User.UserBuilder} for method chaining (i.e. to populate
//         * additional attributes for this user)
//         * @see #roles(String...)
//         */
//        public UserBuilder authorities(GrantedAuthority... authorities) {
//            return authorities(Arrays.asList(authorities));
//        }
//
//        /**
//         * Populates the authorities. This attribute is required.
//         *
//         * @param authorities the authorities for this user. Cannot be null, or contain
//         * null values
//         * @return the {@link User.UserBuilder} for method chaining (i.e. to populate
//         * additional attributes for this user)
//         * @see #roles(String...)
//         */
//        public UserBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
//            this.authorities = new ArrayList<>(authorities);
//            return this;
//        }
//
//        /**
//         * Populates the authorities. This attribute is required.
//         *
//         * @param authorities the authorities for this user (i.e. ROLE_USER, ROLE_ADMIN,
//         * etc). Cannot be null, or contain null values
//         * @return the {@link User.UserBuilder} for method chaining (i.e. to populate
//         * additional attributes for this user)
//         * @see #roles(String...)
//         */
//        public UserBuilder authorities(String... authorities) {
//            return authorities(AuthorityUtils.createAuthorityList(authorities));
//        }
//
//        /**
//         * Defines if the account is expired or not. Default is false.
//         *
//         * @param accountExpired true if the account is expired, false otherwise
//         * @return the {@link User.UserBuilder} for method chaining (i.e. to populate
//         * additional attributes for this user)
//         */
//        public UserBuilder accountExpired(boolean accountExpired) {
//            this.accountExpired = accountExpired;
//            return this;
//        }
//
//        /**
//         * Defines if the account is locked or not. Default is false.
//         *
//         * @param accountLocked true if the account is locked, false otherwise
//         * @return the {@link User.UserBuilder} for method chaining (i.e. to populate
//         * additional attributes for this user)
//         */
//        public UserBuilder accountLocked(boolean accountLocked) {
//            this.accountLocked = accountLocked;
//            return this;
//        }
//
//        /**
//         * Defines if the credentials are expired or not. Default is false.
//         *
//         * @param credentialsExpired true if the credentials are expired, false otherwise
//         * @return the {@link User.UserBuilder} for method chaining (i.e. to populate
//         * additional attributes for this user)
//         */
//        public UserBuilder credentialsExpired(boolean credentialsExpired) {
//            this.credentialsExpired = credentialsExpired;
//            return this;
//        }
//
//        /**
//         * Defines if the account is disabled or not. Default is false.
//         *
//         * @param disabled true if the account is disabled, false otherwise
//         * @return the {@link User.UserBuilder} for method chaining (i.e. to populate
//         * additional attributes for this user)
//         */
//        public UserBuilder disabled(boolean disabled) {
//            this.disabled = disabled;
//            return this;
//        }
//
//        public UserDetails build() {
//            String encodedPassword = this.passwordEncoder.apply(password);
//            return new User(username, encodedPassword, !disabled, !accountExpired,
//                    !credentialsExpired, !accountLocked, authorities);
//        }
//    }
}
