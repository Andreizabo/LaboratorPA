import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Algorithm {
    public Solution solve(Catalog catalog) {
        int n = catalog.getItems().size();
        Model model = new Model("Playlist problem");

        IntVar[] colours = new IntVar[n];
        IntVar[][] matrix = new IntVar[n][];
        for(int i = 0; i < n; ++i) {
            colours[i] = model.intVar("NodeCol_" + i, 0, n - 1);
        }

        for(int i = 0; i < n; ++i) {
            matrix[i] = new IntVar[n];
            CatalogItem item = catalog.getCatalogItem(i);

            for(int j = 0; j < n; ++j) {
                CatalogItem otherItem = catalog.getCatalogItem(j);
                if(item.equals(otherItem)) {
                    matrix[i][j] = model.intVar("Edge_" + i + "_" + j, 0);
                    continue;
                }
                if(item instanceof Movie) {
                    if (otherItem instanceof Movie) {
                        if (((Movie) item).getDirector().equals(((Movie) otherItem).getDirector())
                                || ((Movie) item).getReleaseDate().equals(((Movie) otherItem).getReleaseDate())
                                || ((Movie) item).getRunningTime().equals(((Movie) otherItem).getRunningTime())) {
                            matrix[i][j] = model.intVar("Edge_" + i + "_" + j, 1);
                        }
                        else {
                            matrix[i][j] = model.intVar("Edge_" + i + "_" + j, 0);
                        }
                    } else if (otherItem instanceof Song) {
                        if (((Movie) item).getDirector().equals(((Song) otherItem).getSinger())
                                || ((Movie) item).getReleaseDate().equals(((Song) otherItem).getReleaseDate())
                                || ((Movie) item).getRunningTime().equals(((Song) otherItem).getDuration())) {
                            matrix[i][j] = model.intVar("Edge_" + i + "_" + j, 1);
                        }
                        else {
                            matrix[i][j] = model.intVar("Edge_" + i + "_" + j, 0);
                        }
                    }
                }
                else if(item instanceof Song) {
                    if (otherItem instanceof Movie) {
                        if (((Song) item).getSinger().equals(((Movie) otherItem).getDirector())
                                || ((Song) item).getReleaseDate().equals(((Movie) otherItem).getReleaseDate())
                                || ((Song) item).getDuration().equals(((Movie) otherItem).getRunningTime())) {
                            matrix[i][j] = model.intVar("Edge_" + i + "_" + j, 1);
                        }
                        else {
                            matrix[i][j] = model.intVar("Edge_" + i + "_" + j, 0);
                        }
                    } else if (otherItem instanceof Song) {
                        if (((Song) item).getSinger().equals(((Song) otherItem).getSinger())
                                || ((Song) item).getReleaseDate().equals(((Song) otherItem).getReleaseDate())
                                || ((Song) item).getDuration().equals(((Song) otherItem).getDuration())) {
                            matrix[i][j] = model.intVar("Edge_" + i + "_" + j, 1);
                        }
                        else {
                            matrix[i][j] = model.intVar("Edge_" + i + "_" + j, 0);
                        }
                    }
                }
            }
        }

        for(int i = 0; i < n - 1; ++i) {
            for(int j = i + 1; j < n; ++j) {
                Constraint connected = model.arithm(matrix[i][j], "=", model.intVar(1));
                Constraint notSameColour = model.arithm(colours[i], "!=", colours[j]);
                model.ifThen(connected, notSameColour);
            }
        }

        for(int i = 0; i < n; ++i) {
            model.min(colours[i], model.intVarArray(n, 0, n - 1)).post();
        }

        org.chocosolver.solver.Solution sol = model.getSolver().findSolution();
        if(sol != null) {
            String[] results = sol.toString().split(", ");
            MyPair<Integer, Integer>[] indexColour = new MyPair[n];

            for(int i = 0; i < n; ++i) {
                results[i] = results[i].split("=")[1];
            }
            for(int i = 0; i < n; ++i) {
                indexColour[i] = new MyPair<>(i, Integer.parseInt(results[i]));
            }

            Arrays.sort(indexColour, Comparator.comparingInt(MyPair::getSecond));

            List<List<CatalogItem>> items = new ArrayList<>();

            for(int i = 0; i < n; ++i) {
                if(indexColour[i].getSecond() >= items.size() || items.get(indexColour[i].getSecond()) == null) {
                    items.add(new ArrayList<>());
                }
                items.get(indexColour[i].getSecond()).add(catalog.getCatalogItem(indexColour[i].getFirst()));
            }
            return new Solution(items);
        }
        return null;
    }
}
