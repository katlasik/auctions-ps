package pl.sda.auctions.model.dto;

import javax.validation.constraints.Size;
import java.util.Objects;

public class ChangePasswordForm {

    @Size(min = 6, max = 100, message = "{registration.errorMsg.password}")
    private String oldPassword;

    @Size(min = 6, max = 100, message = "{registration.errorMsg.password}")
    private String newPassword;

    @Size(min = 6, max = 100, message = "{registration.errorMsg.password}")
    private String retypedNewPassword;

    public ChangePasswordForm(String oldPassword, String newPassword, String retypeNewPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.retypedNewPassword = retypeNewPassword;

    }


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String name) {
        this.oldPassword = name;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getRetypedNewPassword() {
        return retypedNewPassword;
    }

    public void setRetypedNewPassword(String retypedNewPassword) {
        this.retypedNewPassword = retypedNewPassword;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChangePasswordForm that = (ChangePasswordForm) o;
        return Objects.equals(oldPassword, that.oldPassword) &&
                Objects.equals(newPassword, that.newPassword) &&
                Objects.equals(retypedNewPassword, that.retypedNewPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oldPassword, newPassword, retypedNewPassword);

    }
}
