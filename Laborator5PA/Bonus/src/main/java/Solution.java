import java.util.List;

public class Solution {
    public List<List<CatalogItem>> playList;

    public Solution(List<List<CatalogItem>> playList) {
        this.playList = playList;
    }

    public void printSolution() {
        System.out.print("\n");
        for(int i = 0; i < playList.size(); ++i) {
            System.out.print("\nOn day " + i + " we can play: ");
            playList.get(i).forEach((item) -> System.out.print("\n\t" + item.getName()));
        }
        System.out.print("\n");
    }
}
