package com.meraj.spring.domain;

public class Guest implements Comparable<Guest> {

    private String name;

    private String creditCardNo;

    public Guest(String name, String creditCardNo) {
        this.name = name;
        this.creditCardNo = creditCardNo;
    }

    public String getName() {
        return name;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    @Override
    public int compareTo(Guest o1) {
        return o1.getCreditCardNo().compareTo(creditCardNo);
    }

    @Override
    public String toString() {
        return "Guest [name=" + name + ", creditCardNo=" + creditCardNo + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((creditCardNo == null) ? 0 : creditCardNo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Guest other = (Guest) obj;
        if (creditCardNo == null) {
            if (other.creditCardNo != null)
                return false;
        } else if (!creditCardNo.equals(other.creditCardNo))
            return false;
        return true;
    }

}
