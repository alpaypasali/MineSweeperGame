class MineBoard {
    private String[][] board;
    private  int row;
    private  int column;
    // Belirtilen satır ve sütun sayılarına göre bir Mayın Tahtası oluşturur
    MineBoard(int row, int column) {
        this.row = row;
        this.column = column;
        this.board = new String[row][column];
    }
    // Mayın tahtasını '-' karakterleri ile doldurur
    public void createBoard() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                board[i][j] = "-";
            }
        }
    }
    // Mayın tahtasını konsola yazdırır
    public void printBoard(){
        for(String[] u : board){
            for(String elem : u){
                System.out.print(elem +" ");
            }
            System.out.println(" ");
        }
    }
    // Belirtilen hücrenin açılıp açılmadığını kontrol eder
    public boolean isCellOpened(int row, int column) {
        return !board[row][column].equals("-");
    }
    // Belirtilen hücreye komşu olan mayınların sayısını tahtada gösterir
    public void calculateCellValues(int row , int column , int value){
        board[row][column] = String.valueOf(value);
    }
    // Oyunun kazanılıp kazanılmadığını kontrol eder
    public boolean isGameWon() {
        int totalCells = row * column;
        int closedCells = 0;
        // Tüm hücrelerin durumunu kontrol et
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                // Eğer hücre açılmamışsa,kapalı hücre sayısını arttır
                if (!isCellOpened(i, j)) {
                    closedCells++;
                }
            }
        }
        // Eğer açılmayan hücre sayısı, toplam hücre sayısının 1/4'üne eşitse, oyun kazanılmıştır
        return closedCells == totalCells / 4;
    }

}