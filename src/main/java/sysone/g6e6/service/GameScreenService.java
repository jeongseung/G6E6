package sysone.g6e6.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import sysone.g6e6.model.Mistake;
import sysone.g6e6.model.PlayRecord;
import sysone.g6e6.model.Quiz;
import sysone.g6e6.repository.MistakeRepository;
import sysone.g6e6.repository.PlayRecordRepository;
import sysone.g6e6.repository.QuizRepository;
import sysone.g6e6.repository.ReviewNoteRepository;

public class GameScreenService {
	private QuizRepository quizRepository = new QuizRepository();
	private PlayRecordRepository playRecordRepository = new PlayRecordRepository();
	private MistakeRepository mistakeRepository = new MistakeRepository();
	private ReviewNoteRepository reviewNoteRepository = new ReviewNoteRepository();

	private Quiz curQuiz;
	private char[][] map = new char[16][16];
	private List<int[]> h_visited = new ArrayList<>();
	private List<int[]> v_visited = new ArrayList<>();
	private boolean isVertical = false;
	private int total_num;
	private int h_num = 0;
	private int v_num = 0;
	private int x, y, idx = 0;
	private List<Quiz> quizzes = new ArrayList<>();
	private List<Quiz> using_words;
	private List<Quiz> wasted_words = new ArrayList<>();

	private HashMap<String, List<?>> resultHashMap = new HashMap<>();
	private List<int[]> resultVerticalCoord = new ArrayList<>();
	private List<Quiz> resultVerticalQuiz = new ArrayList<>();
	private List<int[]> resultHorizontalCoord = new ArrayList<>();
	private List<Quiz> resultHorizontalQuiz = new ArrayList<>();

	// 가로 퀴즈 : 좌표(int[][]), 답,문제(String[])
	//

	public HashMap<String, List<?>> createGame(int subjectId, int total_num) throws Exception {
		quizzes = quizRepository.findBySubjectId(subjectId);
		using_words = new ArrayList<>(quizzes);
		this.total_num = total_num;
		createMap();
		resultHashMap.put("세로좌표", resultVerticalCoord);
		resultHashMap.put("세로퀴즈", resultVerticalQuiz);
		resultHashMap.put("가로좌표", resultHorizontalCoord);
		resultHashMap.put("가로퀴즈", resultHorizontalQuiz);
		return resultHashMap;
	}

	private void createMap() {
		Random rand = new Random(System.currentTimeMillis());
		Collections.shuffle(quizzes);
		int count_loop = 1;
		while ((h_num + v_num) < total_num) {
			// System.out.println(count_loop++);
			// System.out.println(isVertical ? "세로" : "가로");
			x = rand.nextInt(map.length);
			y = rand.nextInt(map.length);
			if (idx == using_words.size()) {
				rand = new Random(System.currentTimeMillis());
				Collections.shuffle(wasted_words);
				using_words.addAll(wasted_words);
				wasted_words.clear();
			}
			// System.out.println(using_words.size());
			curQuiz = using_words.get(idx++);
			// System.out.println(curQuiz.toString());
			String word = curQuiz.getAnswer();
			// System.out.println(x + ", " + y);
			// System.out.println(word);

			if (isVertical) {
				if ((x + word.length() - 1) >= map.length) {
					wasted_words.add(curQuiz);
					count_loop++;
					continue;
				}
				/// chk_pt
				if (count_loop > 500) {
					int[] point = {x, y};
					if (containArr(v_visited, point)) {
						wasted_words.add(curQuiz);
						count_loop++;
						continue;
					}
					if (!checkMap(point, word, false, true)) {
						wasted_words.add(curQuiz);
						count_loop++;
						continue;
					}
					insertMap(word, false);
					h_num++;
					isVertical = !isVertical;
					count_loop = 0;
					/// chk_pt
				} else if (h_visited.isEmpty()) {
					int[] point = {x, y};
					if (containArr(v_visited, point)) {
						wasted_words.add(curQuiz);
						count_loop++;
						continue;
					}
					// System.out.println("1. (" + x + ", " + y + ")에 " + word + "를 세로로 넣을거에요");
					insertMap(word, false);
					h_num++;
					isVertical = !isVertical;
				} else {
					boolean inserted = false;
					Iterator<int[]> iterator = h_visited.iterator();
					while (iterator.hasNext()) {
						int[] a = iterator.next();
						// System.out.println("탐색좌표 : " + a[0] + ", " + a[1]);

						if (word.startsWith(String.valueOf(map[a[0]][a[1]]))
							&& (a[0] + word.length() - 1) < map.length) {
							if (containArr(v_visited, a)) {
								// System.out.println(a[0] + ", " + a[1] + "은 이미 세로칸에도 존재해요");
								count_loop++;
								break;
							}
							if (!checkMap(a, word, false, false)) {
								// System.out.println("주변에 겹치는 문자가 존재해요");
								count_loop++;
								break;
							}
							// System.out.println("2. (" + a[0] + ", " + a[1] + ")에 " + word + "를 세로로 넣을거에요");
							x = a[0];
							y = a[1];
							insertMap(word, false);
							v_num++;
							isVertical = !isVertical;
							inserted = true;
							break;
						} else if (word.endsWith(String.valueOf(map[a[0]][a[1]])) && (a[0] - word.length() + 1) >= 0) {
							if (containArr(v_visited, a)) {
								// System.out.println(a[0] + ", " + a[1] + "은 이미 세로칸에도 존재해요");
								count_loop++;
								break;
							}
							if (!checkMap(a, word, true, false)) {
								// System.out.println("주변에 겹치는 문자가 존재해요");
								count_loop++;
								break;
							}
							// System.out.println("3. (" + a[0] + ", " + a[1] + ")에 " + word + "를 세로로 거꾸로 넣을거에요");
							x = a[0];
							y = a[1];
							insertMap(word, true);
							v_num++;
							isVertical = !isVertical;
							inserted = true;
							break;
						}
					}
					if (!inserted) {
						wasted_words.add(curQuiz);
					}
				}
			} else {
				if ((y + word.length() - 1) >= map.length) {
					wasted_words.add(curQuiz);
					count_loop++;
					continue;
				}
				/// chk_pt
				if (count_loop > 500) {
					int[] point = {x, y};
					if (containArr(v_visited, point)) {
						wasted_words.add(curQuiz);
						count_loop++;
						continue;
					}
					if (!checkMap(point, word, false, true)) {
						wasted_words.add(curQuiz);
						count_loop++;
						continue;
					}
					insertMap(word, false);
					h_num++;
					isVertical = !isVertical;
					count_loop = 0;
					/// chk_pt
				} else if (v_visited.isEmpty()) {
					int[] point = {x, y};
					if (containArr(h_visited, point)) {
						wasted_words.add(curQuiz);
						count_loop++;
						continue;
					}
					// System.out.println("4. (" + x + ", " + y + ")에 " + word + "를 가로로 넣을거에요");
					insertMap(word, false);
					h_num++;
					isVertical = !isVertical;
				} else {
					boolean inserted = false;
					Iterator<int[]> iterator = v_visited.iterator();
					while (iterator.hasNext()) {
						int[] a = iterator.next();
						List<int[]> ck_cord = new ArrayList<>();
						// System.out.println("탐색좌표 : " + a[0] + ", " + a[1]);
						if (word.startsWith(String.valueOf(map[a[0]][a[1]]))
							&& (a[1] + word.length() - 1) < map.length) {
							if (containArr(h_visited, a)) {
								// System.out.println(a[0] + ", " + a[1] + "은 이미 가로칸에도 존재해요");
								count_loop++;
								break;
							}
							if (!checkMap(a, word, false, false)) {
								// System.out.println("주변에 겹치는 문자가 존재해요");
								count_loop++;
								break;
							}
							// System.out.println("5. (" + a[0] + ", " + a[1] + ")에 " + word + "를 가로로 넣을거에요");
							x = a[0];
							y = a[1];
							insertMap(word, false);
							h_num++;
							isVertical = !isVertical;
							inserted = true;
							break;
						} else if (word.endsWith(String.valueOf(map[a[0]][a[1]])) && (a[1] - word.length() + 1) >= 0) {
							if (containArr(h_visited, a)) {
								// System.out.println(a[0] + ", " + (a[1]) + "은 이미 가로칸에도 존재해요");
								count_loop++;
								break;
							}
							if (!checkMap(a, word, true, false)) {
								// System.out.println("주변에 겹치는 문자가 존재해요");
								count_loop++;
								break;
							}
							// System.out.println("6. (" + a[0] + ", " + a[1] + ")에 " + word + "를 가로로 거꾸로 넣을거에요");
							x = a[0];
							y = a[1];
							insertMap(word, true);
							h_num++;
							isVertical = !isVertical;
							inserted = true;
							break;
						}
					}
					if (!inserted) {
						wasted_words.add(curQuiz);
					}
				}
			}
		}
		showMap(map);
	}

	private void showVisited(boolean isV) {
		List<int[]> visited;
		visited = isV ? v_visited : h_visited;
		for (int[] cord : visited) {
			// System.out.print(Arrays.toString(cord));
		}
		// System.out.println();
	}

	private boolean checkMap(int[] start_cord, String word, boolean isReverse, boolean resolveLoop) {
		// System.out.println(word+start_cord[0]+", "+start_cord[1]);

		List<int[]> cord_map = new ArrayList<>();
		cord_map.add(
			new int[] {start_cord[0] + (!isVertical ? 0 : isReverse ? 1 : -1),
				start_cord[1] + (isVertical ? 0 : isReverse ? 1 : -1)});
		cord_map.add(
			new int[] {
				start_cord[0] + word.length() * (!isVertical ? 0 : isReverse ? -1 : 1),
				start_cord[1] + word.length() * (isVertical ? 0 : isReverse ? -1 : 1)});
		int len = word.length();
		if (isReverse) {
			StringBuffer sb = new StringBuffer(word);
			word = sb.reverse().toString();
		}
		if (isVertical) {
			if (!isReverse) {
				// for (int n = resolveLoop ? 0 : 1; n < len; n++) {
				for (int n = 0; n < len; n++) {
					if (map[start_cord[0] + n][start_cord[1]] != word.charAt(n)) {
						cord_map.add(new int[] {start_cord[0] + n, start_cord[1] + 1});
						cord_map.add(new int[] {start_cord[0] + n, start_cord[1]});
						cord_map.add(new int[] {start_cord[0] + n, start_cord[1] - 1});
					}
				}
			} else {
				// for (int n = resolveLoop ? 0 : 1; n < len; n++) {
				for (int n = 0; n < len; n++) {
					if (map[start_cord[0] - n][start_cord[1]] != word.charAt(n)) {
						cord_map.add(new int[] {start_cord[0] - n, start_cord[1] + 1});
						cord_map.add(new int[] {start_cord[0] - n, start_cord[1]});
						cord_map.add(new int[] {start_cord[0] - n, start_cord[1] - 1});
					}
				}
			}
		} else {
			if (!isReverse) {
				// for (int n = resolveLoop ? 0 : 1; n < len; n++) {
				for (int n = 0; n < len; n++) {
					if (map[start_cord[0]][start_cord[1] + n] != word.charAt(n)) {
						cord_map.add(new int[] {start_cord[0] + 1, start_cord[1] + n});
						cord_map.add(new int[] {start_cord[0], start_cord[1] + n});
						cord_map.add(new int[] {start_cord[0] - 1, start_cord[1] + n});
					}
				}
			} else {
				// for (int n = resolveLoop ? 0 : 1; n < len; n++) {
				for (int n = 0; n < len; n++) {
					if (map[start_cord[0]][start_cord[1] - n] != word.charAt(n)) {
						cord_map.add(new int[] {start_cord[0] + 1, start_cord[1] - n});
						cord_map.add(new int[] {start_cord[0], start_cord[1] - n});
						cord_map.add(new int[] {start_cord[0] - 1, start_cord[1] - n});
					}
				}
			}
		}
		for (int[] cord : cord_map) {
			if (resolveLoop) {
				// System.out.println(cord[0] + ", " + cord[1]);
			}
			if (containArr(v_visited, cord) || containArr(h_visited, cord)) {
				return false;
			}
		}
		if (resolveLoop) {
			// System.out.println("flag : "+word);
		}
		return true;
	}

	private void insertMap(String word, boolean isReverse) {
		String flag = (isVertical ? "V :" : "H :") + word;
		StringBuffer sb = new StringBuffer(word);
		word = isReverse ? sb.reverse().toString() : word;

		if (!isVertical) {
			int j = y;
			for (char s : word.toCharArray()) {
				h_visited.add(new int[] {x, j});
				if (isReverse) {
					map[x][j--] = s;
				} else {
					map[x][j++] = s;
				}
			}
			resultHorizontalCoord.add(new int[] {x, isReverse ? j + 1 : y});
			resultHorizontalQuiz.add(curQuiz);

		} else {
			int i = x;
			for (char s : word.toCharArray()) {
				v_visited.add(new int[] {i, y});
				if (isReverse) {
					map[i--][y] = s;

				} else {
					map[i++][y] = s;
				}
			}
			resultVerticalCoord.add(new int[] {isReverse ? i + 1 : x, y});
			resultVerticalQuiz.add(curQuiz);
		}
		// System.out.println(h_num + v_num);
		showMap(map);
	}

	private boolean containArr(List<int[]> visited, int[] a) {
		boolean contains = false;
		for (int[] array : visited) {
			if (Arrays.equals(array, a)) {
				contains = true;
				break;
			}
		}
		return contains;
	}

	private void showMap(char[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (map[i][j] == 0) {
					System.out.print("■" + " ");
				} else {
					System.out.print(map[i][j] + " ");
				}
			}
			System.out.println();
		}
		// System.out.println("현재 V_visited");
		showVisited(true);
		// System.out.println("현재 H_visited");
		showVisited(false);
	}

	public PlayRecord createPlayRecord(int userId, int subjectId, String difficulty, double solveTime) throws
		Exception {
		PlayRecord playRecord = new PlayRecord(null, userId, subjectId, difficulty, solveTime);
		playRecord = playRecordRepository.save(playRecord);
		return playRecord;
	}

	public List<Mistake> createMistakes(List<Quiz> mistakeQuiz, int userId, int subjectId) throws Exception {
		List<Mistake> mistakes = new ArrayList<>();
		for (Quiz quiz : mistakeQuiz) {
			Mistake mistake = new Mistake(null, quiz.getId(), userId, subjectId, null);
			mistakes.add(mistakeRepository.save(mistake));
		}
		return mistakes;
	}

	public void reportError(int userId, int quizId) throws Exception {
		reviewNoteRepository.saveErrorReports(userId, quizId);
	}
}
