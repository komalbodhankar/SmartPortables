public enum UserType {
    Customer, StoreManager, SalesMan;

    public static UserType getEnum(String userType) {
        for(UserType userTypeEnum: UserType.values()) {
            if(userTypeEnum.toString().equalsIgnoreCase(userType)) {
                return userTypeEnum;
            }
        }
        return null;
    }
}