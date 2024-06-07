package application.java;

public enum BlockChainCommand {
    GET_ALL_FILES("GetAllFiles"),
    CREATE_FILE("CreateFile"),
    READ_FILE("ReadFile"),
    DELETE_FILE("DeleteFile"),
    FILE_EXISTS("FileExists"),
    UPDATE_FILE("UpdateFile");

    private String value;

    BlockChainCommand(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
