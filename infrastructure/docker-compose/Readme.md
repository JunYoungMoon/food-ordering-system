# Kafka 클러스터 설정 가이드

## 사전 요구사항
- Docker 및 Docker Compose 설치
- netcat (nc) 설치 (Zookeeper 상태 확인용)
- 충분한 메모리 (최소 4GB 권장)

## 실행 순서

### 1. Zookeeper 실행
Kafka 클러스터의 메타데이터를 관리하는 Zookeeper를 먼저 실행합니다.
```bash
docker compose -f common.yml -f zookeeper.yml up
```

### 2. Zookeeper 상태 확인
새 터미널에서 Zookeeper가 정상적으로 실행되고 있는지 확인합니다.
```bash
echo ruok | nc localhost 2181
```
정상 응답: `imok`

### 3. Kafka 클러스터 실행
Zookeeper가 정상 작동하면 Kafka 브로커들을 실행합니다.
```bash
sudo docker compose -f common.yml -f kafka_cluster.yml up
```

### 4. PostgreSQL 실행
데이터베이스 서버를 실행합니다.
```bash
sudo docker compose -f common.yml -f postgresql.yml up
```

### 5. Kafka 토픽 초기화
필요한 토픽들을 생성하고 초기 설정을 수행합니다.
```bash
sudo docker compose -f common.yml -f init_kafka.yml up
```

## CMAK (Cluster Manager for Apache Kafka) 설정

### CMAK 접속
- URL: http://localhost:9000
- 초기 접속 시 클러스터 추가 필요

### 클러스터 추가 방법
1. CMAK 웹 인터페이스 접속
2. "Cluster" → "Add Cluster" 클릭
3. 다음 정보 입력:
   - Cluster Name: `food-ordering-system-cluster`
   - Cluster Zookeeper Hosts: `zookeeper:2181`
   - Kafka Version: 2.4
4. "Save" 클릭

![CMAK 클러스터 추가](../../images/img.png)