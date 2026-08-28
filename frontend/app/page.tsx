import SearchBar from "@/components/common/SearchBar";
import PopularStocks from "@/components/common/PopularStocks";

export default function Home() {
  return (
    <main>
      <h1>Fundify</h1>
      <p>흩어진 기업 정보를 한 화면에서 비교하다</p>

      <SearchBar />
      <PopularStocks />
    </main>
  );
}
