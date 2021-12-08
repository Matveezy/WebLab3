package entity;


public class Result {
    private Float x;
    private Float y;
    private Float r;
    private String currentTime;
    private long executionTime;
    private boolean result;

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "Result{" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", currentTime='" + currentTime + '\'' +
                ", executionTime=" + executionTime +
                ", result=" + result +
                '}';
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public Float getR()
    {
        return r;
    }

    public void setR(Float r) {
        this.r = r;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
