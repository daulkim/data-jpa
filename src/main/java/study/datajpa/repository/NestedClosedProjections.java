package study.datajpa.repository;

// 중첩일 경우 root만 최적화 join된 건 최적화X 다가져옴
public interface NestedClosedProjections {

    String getUsername();
    TeamInfo getTeam();

    interface  TeamInfo{
        String getName();
    }
}
