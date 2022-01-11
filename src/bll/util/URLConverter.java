package bll.util;

public class URLConverter
{
    /**
     * Here we have our URL converter, we have a single method in here, changing the filepath input to an URI (Not URL)
     */
    public static String fileLinkToURI(String filePath)
    {
        return "file:/" + filePath.replace("\\", "/");
    }

}
