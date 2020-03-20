package golhar.cocomo.zinger.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CollegeModel implements Parcelable {
    private Integer id;
    private String name;
    private String iconUrl;
    private String address;
    private Integer isDelete;

    protected CollegeModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        iconUrl = in.readString();
        address = in.readString();
        if (in.readByte() == 0) {
            isDelete = null;
        } else {
            isDelete = in.readInt();
        }
    }

    public static final Creator<CollegeModel> CREATOR = new Creator<CollegeModel>() {
        @Override
        public CollegeModel createFromParcel(Parcel in) {
            return new CollegeModel(in);
        }

        @Override
        public CollegeModel[] newArray(int size) {
            return new CollegeModel[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CollegeModel() {
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
        this.address = address;
        this.isDelete = isDelete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "CollegeModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", address='" + address + '\'' +
                ", isDelete=" + isDelete +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(iconUrl);
        dest.writeString(address);
        if (isDelete == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(isDelete);
        }
    }
}
