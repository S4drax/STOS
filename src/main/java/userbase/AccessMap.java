package userbase;

import content.Folder;
import content.MenuElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder
@Getter
@Setter
public class AccessMap {
    Map<String, Map<String, MenuElement>> accessElementMap;
    Map<String, Map<String, Folder>> accessFolderMap;
}
